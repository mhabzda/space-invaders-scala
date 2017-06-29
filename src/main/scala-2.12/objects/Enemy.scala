package objects
import commons.Commons
import scala.util.Random

class Enemy (a: Int, b: Int, var almostDied: Boolean = false,
             var bomb: Bomb = new Bomb(), var rand: Random = new Random)
             extends MovingObject(a, b, Commons.ENEMY_WIDTH, Commons.ENEMY_HEIGHT, dx = 1) {

    loadImage("enemy.png")

    def tryToShoot() {
        val random = rand.nextInt % 450
        if (random == 1 && !bomb.visible && visible) {
          bomb.x = this.x + Commons.ENEMY_WIDTH / 2
          bomb.y = this.y + Commons.ENEMY_HEIGHT
          bomb.visible = true
        }
    }

    override def move() {
        super.move()
    }
}
