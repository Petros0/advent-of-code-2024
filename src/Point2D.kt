data class Point2D(val x: Int, val y: Int) {

    operator fun plus(other: Point2D): Point2D =
        Point2D(x + other.x, y + other.y)

    operator fun minus(other: Point2D): Point2D =
        Point2D(x - other.x, y - other.y)

    companion object {
        val NORTH = Point2D(0, -1)
        val EAST = Point2D(1, 0)
        val SOUTH = Point2D(0, 1)
        val WEST = Point2D(-1, 0)
    }
}
