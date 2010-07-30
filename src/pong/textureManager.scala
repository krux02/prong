/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pong

import org.lwjgl.examples.spaceinvaders
import java.io.FileInputStream
import org.newdawn.slick.opengl._

object textureManager {
	val glas = loadImage("resources/glas.png")
	val ball = loadImage("resources/ball.png")

	private def loadImage(filename:String):Texture = {
		// TODO hier fehler
		val format = filename.split("\\.").last.toUpperCase
		TextureLoader.getTexture( format, new FileInputStream(filename));
	}
}
