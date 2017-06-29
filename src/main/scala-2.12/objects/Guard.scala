package objects
import commons.Commons
import scala.collection.mutable.ListBuffer
import scala.swing.Graphics2D


class Guard(val x: Int, val y: Int, var squares: ListBuffer[Square] = new ListBuffer[Square]) {

    for (i <- 0 to 2)
        for (j <- 0 to 4)
            squares += new Square(x + Commons.SQUARE_SIZE * j, y + Commons.SQUARE_SIZE * i)


    def collisionWith(obj: MovingObject) {
        for (square <- squares) {
            if (square.visible && (square intersects obj.getBoundary)) {
                square.visible = false
                obj.visible = false
            }
        }
    }

    def draw(g: Graphics2D) {
        for (square <- squares) {
            if (square.visible)
                square draw g
        }
    }
}
