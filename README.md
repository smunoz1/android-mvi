# Patrón arquitectónico MVI (Model-View-intent).

![State](https://img.shields.io/badge/Kotlin-v1.5.31-blueviolet)
![State](https://img.shields.io/badge/Gradle-v7.3.3-blue)

La arquitectura MVI tiene como objetivo simplificar la gestión de los estados y los flujos de usuarios. Además, permite la escalabilidad del código y facilita las pruebas unitarias. 

## Desglosando MVI (Model-View-Intent)

## Model
Contiene una representación de estados de la interfaz, la cual es cambiada con lógica reductora. Los cambios de datos se propagan a la capa de vista como un flujo de estados.

¿Cómo se representan los estados si quiero hacer un flujo de recuperación de datos desde el servidor?

- Ingreso a la app, pantalla por defecto: **Default**
- Inicia la intención: **Loading**
- Datos se recuperaron con éxito: **DisplayData**
- Ocurrió un error en la petición: **Error**
- Servidor respondió ok, pero sin datos: **EmptyData**

## View

View se encarga de observar los estados y va renderizando la vista. Ejemplo:

<img width="1364" alt="Captura de Pantalla 2022-12-26 a la(s) 10 34 42" src="https://user-images.githubusercontent.com/104868802/209554453-00d87051-3f7b-4412-9de5-9f7c23dff5c7.png">

## Intent

Se traduce como la intención, sería cualquier acción iniciadora que el usuario realice en la Vista, el cual generará un flujo unidireccional.

<img width="1144" alt="Captura de Pantalla 2022-12-26 a la(s) 10 49 03" src="https://user-images.githubusercontent.com/104868802/209555558-4af291b6-4efe-4326-b018-b0cf64125f19.png">


## ¿Cómo se representa?

Para mantener un código más legible, escalable y ordenado, se agregaron componentes que ayudan con el tema del flujo MVI.

En resumen, se trabaja con:

- **Intent**: representa las acciones del usuario sobre componentes UI. Se visualiza en el código como una ```sealed class```.


```
internal sealed class UserUIntent {
    object PressingBtnGetListUserUIntent : UserUIntent()
    object RetryUIntent : UserUIntent()
}

```
- **Action**: Se representan las acciones, que son una interpretación de las intenciones que genera el usuario. Se visualiza en el código como una ```sealed class```.

```
internal sealed class UserAction {
    object GetListUserAction : UserAction()
}
```

- **Processor**: es el encargado de tomar la acción y aplicar lógica sobre ella. Se trabaja con asincronía. Acá se pueden recuperar datos desde el repositorio, o también, se hacen las validaciones requeridas desde la vista. El Processor, una vez hecha la acción (```Action```), retorna resultados (```result```).  Se representa como una ```class```

```
internal class UserProcessor {
    private val repository = POCDataRepository()

    fun actionProcessor(actions: UserAction): Flow<UserResult> =
        when (actions) {
            UserAction.GetListUserAction -> loadListUserProcessor()
        }

    private fun loadListUserProcessor(): Flow<UserResult.GetListUserResult> =
        repository.getListUser()
            .map { list ->
                if (list.isEmpty()) {
                    Empty
                } else {
                    Success(list)
                }
            }.onStart {
                emit(InProgress)
            }.catch {
                emit(Error)
            }.flowOn(Dispatchers.IO)
}
```

- **Result**: Se representan los resultados. Estos son utilizados por el ```processor``` para retornar un resultado asociado a una ```Action```. La declaración en el código de las clases ```Action``` y ```Result``` deben tener, por norma, el mismo sufijo. Se visualiza en el código como una ```sealed class```.

```
internal sealed class UserResult {
    sealed class GetListUserResult : UserResult() {
        object InProgress : GetListUserResult()
        data class Success(val listUser: List<String>) : GetListUserResult()
        object Error : GetListUserResult()
        object Empty : GetListUserResult()
    }
}
```

- **Reducer**: ¡Es la maquina de estados! es el encargado de evaluar todos los resultados y cambiar a un nuevo estado. ¿Cómo funciona? el reducer evalua el estado anterior e interpreta el resultado a traves de una función infix de este estado. Esta función infix, se encarga de relacionar el nuevo ```Result``` con un nuevo ```State```. Se representa como una ```class```

```

internal class UserReducer {

    infix fun UserUiState.reduceWith(result: UserResult): UserUiState {
        return when (val previousState = this) {
            is LoadingUiState -> previousState reduceWith result
            is DisplayUserUiState -> previousState reduceWith result
            is EmptyUiState -> previousState reduceWith result
            is ErrorUiState -> previousState reduceWith result
            is DefaultUiState -> previousState reduceWith result
        }
    }

    private infix fun LoadingUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.GetListUserResult.InProgress -> LoadingUiState
        is UserResult.GetListUserResult.Success -> DisplayUserUiState(
            listUser = result.listUser
        )
        is UserResult.GetListUserResult.Error -> ErrorUiState
        is UserResult.GetListUserResult.Empty -> EmptyUiState
    }

    private infix fun DisplayUserUiState.reduceWith(result: UserResult) =
        when (result) {
            is UserResult.GetListUserResult.InProgress -> LoadingUiState
            else -> throw unsupportedReduceCase()
        }

    private infix fun ErrorUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.GetListUserResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }
    private infix fun EmptyUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.GetListUserResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }
    private infix fun DefaultUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.GetListUserResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }

    private fun unsupportedReduceCase() = RuntimeException()

}
}
```

- **State**: Se representan los estados. Estos estados son cambiados por el ```reducer``` y son escuchados por la vista donde se asocian nuevos renderizados a estos estados. Se visualiza en el código como una ```sealed class```.

```
internal sealed class UserUiState {
    object DefaultUiState : UserUiState()
    object LoadingUiState : UserUiState()
    data class DisplayUserUiState(
        val listUser: List<String>,
    ) : UserUiState()
    object ErrorUiState : UserUiState()
    object EmptyUiState : UserUiState()
}
```

- **ViewModel**: es el encargado de mantener el flujo de comunicación entre todas los componentes.

```
internal class UserViewModel : ViewModel() {
    private val reducer = UserReducer()
    private val processor = UserProcessor()

    val defaultUiState: UserUiState = UserUiState.DefaultUiState
    private val uiState: MutableStateFlow<UserUiState> = MutableStateFlow(defaultUiState)

    fun processUserIntentsAndObserveUiStates(
        userIntents: Flow<UserUIntent>,
        coroutineScope: CoroutineScope = viewModelScope,
    ) {
        userIntents.buffer()
            .flatMapMerge { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }
            .scan(defaultUiState) { previousUiState, result ->
                with(reducer) { previousUiState reduceWith result }
            }
            .onEach {
                uiState.value = it
            }
            .launchIn(coroutineScope)
    }

    private fun UserUIntent.toAction(): UserAction {
        return when (this) {
            UserUIntent.PressingBtnGetListUserUIntent -> UserAction.GetListUserAction
            UserUIntent.RetryUIntent -> UserAction.GetListUserAction
        }
    }

    fun uiState(): StateFlow<UserUiState> = uiState
}
```

- **IntentHandler**: Facilita la creación de funciones que van a emitir un ```Intent``` desde la vista. Se visualiza en el código como una ```class``` y su creación queda expuesta en la capa de UI de la estructura de package.
```
class UserIntentHandler {
    private val userIntents = MutableSharedFlow<UserUIntent>()
    var coroutineScope: CoroutineScope? = null

    internal fun userIntents(): Flow<UserUIntent> = userIntents.asSharedFlow()

    fun getListUserUIntent() {
        coroutineScope?.launch {
            userIntents.emit(PressingBtnGetListUserUIntent)
        }
    }

    fun retryIntent() {
        coroutineScope?.launch {
            userIntents.emit(RetryUIntent)
        }
    }
}
```

- **Paso de datos entre componentes**: Si se necesita pasar datos entre componentes se pueden utilizar ```data class``` en las ```sealed class```. Ejemplo:

```object ErrorUiState : UserUiState()``` cambia a ```data class ErrorUiState(val message: String) : UserUiState()```

![image](https://user-images.githubusercontent.com/104868802/209144660-5865736b-6f19-48b8-ac46-c175d4bdd91b.png)

## Objetivos
- Exponer conceptualmente cómo se constituye el flujo de MVI.
- Aplicar flujo MVI en proyecto.
- Crear un flujo MVI en repositorio de evaluación.


