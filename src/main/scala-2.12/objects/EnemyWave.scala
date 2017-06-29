package objects
import commons.Commons
import game.Board
import scala.collection.mutable.ListBuffer
import scala.swing.Graphics2D

class EnemyWave(var enemies: ListBuffer[Enemy] = new ListBuffer[Enemy],
                var numberOfEnemies: Int = 32, var enemySpeed: Int = 1) {

    for (i <- 0 to 3)
        for (j <- 0 to 7)
            enemies += new Enemy(Commons.ENEMY_X + 32 * j, Commons.ENEMY_Y + 32 * i)

    def draw(g: Graphics2D, board: Board) {
        for (enemy <- enemies) {
          if (enemy.visible)
              enemy draw(g, board)
          if (enemy.bomb.visible)
              enemy.bomb draw(g, board)
        }
    }

    def reachedTheGround(): Boolean = {
        for (enemy <- enemies) {
            if(enemy.visible && enemy.y + enemy.height > Commons.GUARD_POSY)
                return true
        }
        false
    }

    def fixStatus() {
        for (enemy <- enemies) {
            if (enemy.dying) {
                enemy.almostDied = true
                enemy.visible = false
            }
            else if (enemy.almostDied) {
                enemy.visible = false
                enemy.almostDied = false
            }
            else if (enemy.visible)
              enemy move()
        }
    }

    def bombMove() {
        for (enemy <- enemies)
            if (enemy.bomb.visible)
                enemy.bomb move()
    }

    def shooting() {
        for (enemy <- enemies)
            enemy tryToShoot()
    }

    def accelerateIfNeeded() {
        var ifNumberOfEnemiesDecreased = false
        if (numberOfEnemies == 16) {
            enemySpeed = 2
            ifNumberOfEnemiesDecreased = true
        }
        if (numberOfEnemies == 8) {
            enemySpeed = 3
            ifNumberOfEnemiesDecreased = true
        }
        if (ifNumberOfEnemiesDecreased) {
          for (enemy <- enemies) {
            if (enemy.dx > 0)
                enemy.dx = enemySpeed
            else
                enemy.dx = -enemySpeed
          }
        }
    }

    def turnAroundIfHitTheWall() {
        for (enemy <- enemies) {
            if (enemy.x > Commons.B_WIDTH - Commons.ENEMY_WIDTH) {
                for (enemyReversed <- enemies) {
                    enemyReversed.dx = -enemySpeed
                    enemyReversed.y += 15
                }
                return
            }
            if (enemy.x < 0) {
                for (enemyReversed <- enemies) {
                    enemyReversed.dx = enemySpeed
                    enemyReversed.y += 15
                }
                return
            }
        }
    }
}