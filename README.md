# POC MVI

![State](https://img.shields.io/badge/Kotlin-v1.5.31-blueviolet)
![State](https://img.shields.io/badge/Gradle-v7.3.3-blue)

POC destinada para ejemplificar el patrón arquitectónico MVI (Model-View-intent).

## Desglosando MVI (Model-View-Intent)

## Model

Representa una clase que mantiene estados de app. Ejemplo:

¿Cómo serían lo estados si quiero hacer un flujo de recuperación de datos desde servidor?

- Ingreso a la app, pantalla por defecto: **DefaultUiState**
- Inicia la intención: **LoadingUiState**
- Datos se recuperaron con éxito: **DisplayUiState**
- Ocurrió un error en la petición: **ErrorUiState**
- Servidor respondió ok, pero sin datos: **EmptyUiState**

## View

View se encarga de observar los estados y va renderizando la vista. Según los estados de la app la vista irá cambiando.

![image](https://user-images.githubusercontent.com/104868802/209144834-88b5853c-753f-445b-b592-6b0f65e70527.png)


## Intent

Se traduce como la intención, sería cualquier acción iniciador que el usuario realice en la Vista, el cual generará un flujo unidireccional.

![image](https://user-images.githubusercontent.com/104868802/209144931-f9634e58-1a34-4475-82ec-29ffbedc6ee8.png)


## ¿Cómo se representa?
![image](https://user-images.githubusercontent.com/104868802/209144660-5865736b-6f19-48b8-ac46-c175d4bdd91b.png)

