package cl.mobdev.features.mvi.navigation

internal sealed class POCRoutes(val path: String) {
    object User : POCRoutes(path = "User")
}
