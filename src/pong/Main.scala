/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pong

import org.lwjgl._
import opengl.{Display,GL11,DisplayMode}
import GL11._
import input._
import math._
import tools.RenderPrimitives

object Main{
	val GAME_TITLE = "My Game"
	val FRAMERATE = 60
	val width = 800
	val height = 600

	val player = new Player(color=(1,0,0));
	val player2 = new Player(color=(0,0,1));

	var finished = false
	var rotation = 0.0f

	def main(args:Array[String]){
		var fullscreen = false
		for(arg <- args){
			arg match{
				case "-fullscreen" =>
					fullscreen = true
			}
		}

		init(fullscreen)
		run
	}

	def init(fullscreen:Boolean){
		{
			import Display._
			setTitle(GAME_TITLE)
			setFullscreen(fullscreen)
			setVSyncEnabled(true)
			setDisplayMode(new DisplayMode(width,height))
			create()
		}

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND)
		glBlendFunc(GL_SRC_ALPHA, GL_ONE)
		//glEnable(GL_CULL_FACE)
		glEnable(GL_LIGHTING)
		glEnable(GL_LIGHT0)

		//glPolygonMode(GL_FRONT, GL_FILL);
		adjustcam

		player2.pos.moveAbsolute(0, -GameField.size, 0)
		player2.pos.rotate(-Pi.toFloat/2, 1, 0, 0)
		player.pos.moveAbsolute(0, GameField.size, 0)
		player.pos.rotate(Pi.toFloat/2, 1, 0, 0)
	}

	def adjustcam(){
		val v = Display.getDisplayMode.getWidth.toFloat/Display.getDisplayMode.getHeight.toFloat
		glMatrixMode(GL_PROJECTION)
		glLoadIdentity
		glFrustum(-v,v,-1,1,1,100)
		glMatrixMode(GL_MODELVIEW)
	}

	def cleanup(){
		Display.destroy()
	}

	def run(){
		while(!finished){
			Display.update()
			
			logic()
			render()

			Display.sync(FRAMERATE)
		}
	}

	def logic(){
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			finished = true
		if(Display.isCloseRequested)
			finished = true

		var ry = 0
		var rx = 0

		if(Keyboard.isKeyDown(Keyboard.KEY_I))
			ry += 1
		if(Keyboard.isKeyDown(Keyboard.KEY_K))
			ry -= 1
		if(Keyboard.isKeyDown(Keyboard.KEY_J))
			rx -= 1
		if(Keyboard.isKeyDown(Keyboard.KEY_L))
			rx += 1

		player addPos (rx.toFloat*0.1f, ry.toFloat*0.1f)
		player2 setPos mapMouseToGameField
	}

	def mapMouseToGameField : (Float,Float) = {
		val x = Mouse.getX.toFloat-width/2
		val y = -Mouse.getY.toFloat+height/2
		return(x / height * 2 * GameField.size,y / height * 2 * GameField.size)
	}

	def render(){
		
		glDisable(GL_DEPTH_TEST)
		glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA)

		glLoadIdentity
		glTranslatef(-1,-1,-1)
		glDisable(GL_LIGHTING)
		glColor4f(0,0,0,0.07f)
		RenderPrimitives.rect(2,2)
		glEnable(GL_LIGHTING)

		glEnable(GL_DEPTH_TEST)
		glClear( GL_STENCIL_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity
		

		
		glTranslatef(0,0,-20)
		glRotatef(-90,1,0,0)
		//glRotatef(rotation,0,0,1)
		
		glColor3f(0,1,0)


		glDisable(GL_LIGHTING)
		
		GameField.draw
		EffectManager.draw
	}
}
