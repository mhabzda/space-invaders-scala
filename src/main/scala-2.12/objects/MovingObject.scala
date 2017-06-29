package objects
import javax.swing.ImageIcon
import game.Board
import scala.swing._

abstract class MovingObject (var x: Int, var y: Int, var width: Int = 0, var height: Int = 0,
                             var image: Image = null, var dx: Int = 0, var dy: Int = 0,
                             var dying: Boolean = false, var visible: Boolean = true) {

    def loadImage(imageName: String): Unit = {
        val imageIcon = new ImageIcon(imageName)
        image = imageIcon.getImage
    }

    def explosion(): Unit = {
        loadImage("explosion.png")
        dying = true
    }

    def getBoundary = new Rectangle(x, y, width, height)

    def collisionWith(obj: MovingObject): Boolean = getBoundary intersects obj.getBoundary

    def draw(g: Graphics2D, board: Board): Boolean = g drawImage(image, x, y, width, height, null)

    def move(): Unit = {
        x += dx
        y += dy
    }
}
