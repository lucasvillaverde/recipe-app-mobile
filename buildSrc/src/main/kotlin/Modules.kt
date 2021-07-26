object Modules {
    val common = ":common"
    val app = ":app"

    object Features {
        val favorite_recipes = ":features:favorite_recipes"
        val recipes = ":features:recipes"

        val allFeatures = arrayListOf(
            favorite_recipes,
            recipes
        )
    }
}