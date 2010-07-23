/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pong

class Player(color:(Float,Float,Float)){
	private var x = 0.0f
	def getX = x
	private var y = 0.0f
	def getY = y

	var size = 2
	val pos = new Pos3d()
	var alpha = 1.0f

	def addPos( t:(Float,Float) ) {
		setPos((x+t._1,y+t._2))
	}

	def setPos( t:(Float,Float) ) {
		if(t._1 + size > GameField.size)
			x = GameField.size-size;
		else if(t._1 - size < -GameField.size)
			x = -GameField.size+size;
		else
			x = t._1

		if(t._2 + size > GameField.size)
			y = GameField.size-size;
		else if(t._2 - size < -GameField.size)
			y = -GameField.size+size;
		else
			y = t._2
	}

	def draw = {
		import org.lwjgl.opengl.GL11._
		import tools.RenderPrimitives._

		glPushMatrix
		val matrix = pos.getGlMatrix

		glMultMatrix(matrix)
		glTranslatef(x,y,0)


		alpha = 0.1f + (alpha-0.1f) * 0.95f
		glColor4f(color._1, color._2, color._3, alpha)
		glDisable(GL_LIGHTING)
		centeredRect(size,size)
		glEnable(GL_LIGHTING)

		glPopMatrix

		val effectpos = new Pos3d
		effectpos.moveAbsolute(pos.position.x, pos.position.y, pos.position.z)
		effectpos.orientation.load(pos.orientation)
		effectpos.moveRelative(x, -y, -0.01f)

		EffectManager.addSplash(effectpos)

	}
}
