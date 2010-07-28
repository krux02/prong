/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pong

import scala.util.Random
import math._
import org.lwjgl.opengl.GL11._

object TheBall{
	val precision = 100
	val size = 0.5f
	var pos = (0.0f,0.0f,0.0f)
	var vel = (Random.nextFloat/5/precision,Random.nextFloat/5/precision,Random.nextFloat/500/precision)
	

	def update():Unit = {
		(pos,vel) match {
			case ((x,y,z),(vx,vy,vz)) =>
				pos = (x+vx,y+vy,z+vz)


				var h1 = 1.0f
				var h2 = 1.0f
				var h3 = 1.0f

				if(abs(x+vx) + size > GameField.size){
					h1 = -1.05f
					val splashpos = new Pos3d()
					if(x>0){
						GameField.right.collision
						splashpos.moveAbsolute(pos._1+size, pos._2, pos._3)
						splashpos.rotate(Pi.toFloat/2, 0, 1, 0)
						EffectManager.addSplash(splashpos)
					}
					else{
						GameField.left.collision
						splashpos.moveAbsolute(pos._1-size, pos._2, pos._3)
						splashpos.rotate(Pi.toFloat/2, 0, -1, 0)
						EffectManager.addSplash(splashpos)
					}
				}
				if(abs(y+vy) + size > GameField.size){
					val splashpos = new Pos3d()
					if(y>0){
						GameField.front.collision
						splashpos.moveAbsolute(pos._1, pos._2+size, pos._3)
						splashpos.rotate(Pi.toFloat/2, 1, 0, 0)
						EffectManager.addSplash(splashpos)
					}
					else{
						GameField.back.collision
						splashpos.moveAbsolute(pos._1, pos._2-size, pos._3)
						splashpos.rotate(Pi.toFloat/2, -1, 0, 0)
						EffectManager.addSplash(splashpos)
					}
					h2 = -1.05f
				}
				if(abs(z+vz) + size > GameField.size){
					val splashpos = new Pos3d()
					if(y>0){
						GameField.front.collision
						splashpos.moveAbsolute(pos._1, pos._2, pos._3+size)
						splashpos.rotate(Pi.toFloat/2, 1, 0, 0)
						EffectManager.addSplash(splashpos)
					}
					else{
						GameField.back.collision
						splashpos.moveAbsolute(pos._1, pos._2, pos._3-size)
						splashpos.rotate(Pi.toFloat/2, -1, 0, 0)
						EffectManager.addSplash(splashpos)
					}
					h3 = -1.05f
				}

				vel = (vx*h1,vy*h2,vz*h3)
		}
	}

	def draw() {
		import tools.RenderPrimitives._

		for(i<-1 to precision)
			update
		

		glPushMatrix
		glTranslatef(pos._1,pos._2,pos._3)
		
		//renderThing
		
		glBegin(GL_LINES)
		glColor3f(1,0,0)
		glVertex3f( 0,0,0)
		glVertex3f( -pos._1-GameField.size,0,0)
		glColor3f(0,1,0)
		glVertex3f( 0,0,0)
		glVertex3f( 0,-pos._2-GameField.size,0)
		glColor3f(0,0,1)
		glVertex3f( 0,0,0)
		glVertex3f( 0,0,-pos._3-GameField.size)
		glEnd
		
		glEnable(GL_LIGHTING)
		glRotatef(90,1,0,0)
		glColor4f(1,1,1,1);
		sphere(size)
		glPopMatrix
		glDisable(GL_LIGHTING)
	}
}
