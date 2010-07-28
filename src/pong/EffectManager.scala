/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pong

import org.lwjgl.opengl.GL11._
import scala.math._
import org.newdawn.slick._

abstract class Effect{
	def update
	def draw
	def alive:Boolean
}

class Splash(pos:Pos3d) extends Effect{
	var size=0f
	var alpha=1.0f
	var rotation=0.0f

	override def update = {
		size = 1-(1-size)*0.95f
		alpha -= 0.025f
		rotation += 0.5f
		this
	}

	override def draw = {
		glDisable(GL_LIGHTING)

		glPushMatrix
		glMultMatrix(pos.getGlMatrix)
		glColor4f(1,1,1,alpha)

		glScalef(size,size,size)

		glBegin(GL_LINES)
		for(i <- 1 to 36){
			glVertex2d(sin((10*i).toRadians),cos((10*i).toRadians))
		}
		glEnd
		glPopMatrix
	}

	override def alive = 	alpha > 0

}



object EffectManager {
	var effects:List[Effect] = Nil
	def draw(){
		for(e <- effects){
			e.update
			e.draw
		}
		effects = effects.filter(a => a.alive)
	}

	def addSplash(pos:Pos3d){
		effects ::= new Splash(pos)
	}
}

