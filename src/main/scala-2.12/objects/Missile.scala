package objects
import commons.Commons

class Missile(a: Int = 1, b: Int = 1)
    extends MovingObject(a, b, Commons.MISSILE_WIDTH, Commons.MISSILE_HEIGHT, dy = -Commons.MISSILE_SPEED) {

    loadImage("missile.png")

    override def move(): Unit = {
        if (y <= 0) {
            visible = false
        }
        super.move()
    }
}
