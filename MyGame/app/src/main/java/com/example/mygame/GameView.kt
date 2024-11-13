package com.example.mygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView : SurfaceView, Runnable {
    private var playing = false
    private var gameThread: Thread? = null
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var canvas: Canvas
    private lateinit var slime: Slime

    private lateinit var mushrooms: Mushrooms
    private val paint = Paint()

    private var screenHeight = 0
    private var screenWidth = 0

    // Background layers with different scroll speeds
    private lateinit var backgroundLayers: List<Pair<Bitmap, Float>>
    private val backgroundScrollX = mutableListOf<Float>()

    constructor(context: Context?, width: Int, height: Int) : super(context) {
        init(context!!, width, height)
    }

    private fun init(context: Context, width: Int, height: Int) {
        surfaceHolder = holder
        slime = Slime(context, width, height)

        mushrooms = Mushrooms(context, width, height)

        // Set screen dimensions
        screenWidth = width
        screenHeight = height

        // Load decorative background images with their respective speeds
        backgroundLayers = listOf(
            Pair(R.drawable.b1, 0.2f), // Farthest, slowest
            Pair(R.drawable.b2, 0.4f),
            Pair(R.drawable.b3, 0.6f),
            Pair(R.drawable.b4, 0.8f),
            Pair(R.drawable.b5, 1.0f),
            Pair(R.drawable.b6, 1.2f)// Closest, fastest
        ).map { (resource, speed) ->
            Pair(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(context.resources, resource),
                    screenWidth,
                    screenHeight,
                    false
                ),
                speed
            )
        }

        // Initialize scroll positions for each background layer
        backgroundScrollX.addAll(List(backgroundLayers.size) { 0f })
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    override fun run() {
        while (playing) {
            update()
            draw()
            controlFrameRate()
        }
    }

    private fun update() {
        if (slime.isAlive) {
            slime.update()

            mushrooms.update()

            // Update background positions for parallax scrolling
            for (i in backgroundScrollX.indices) {
                backgroundScrollX[i] -= backgroundLayers[i].second * 5 // Speed multiplier
                if (backgroundScrollX[i] <= -screenWidth) {
                    backgroundScrollX[i] = 0f
                }
            }

            // Check if slime collides with mushrooms
            if (mushrooms.checkCollision(slime)) {
                resetGame()
            }
        }
    }

    private fun draw() {
        if (surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()
            canvas.drawColor(Color.WHITE)

            // Save the canvas state
            canvas.save()

            // Calculate the center position of the screen
            val centerX = (screenWidth / 2).toFloat()

            // Draw background layers with parallax effect
            for (i in backgroundScrollX.indices) {
                val (bitmap, _) = backgroundLayers[i]
                var scrollX = backgroundScrollX[i]

                // Center the background layer based on the screen width and its scroll position
                scrollX = (scrollX + screenWidth / 2 - centerX) % screenWidth

                // Draw current and next positions for seamless looping
                canvas.drawBitmap(bitmap, scrollX, (screenHeight-bitmap.height)/2f, paint)
                canvas.drawBitmap(bitmap, scrollX + screenWidth, (screenHeight-bitmap.height)/2f, paint)
            }

            // Draw the floor (which now includes the floor hitbox)
            //floor.draw(canvas, paint)

            // Draw the slime on top of the background and floor layers
            canvas.drawBitmap(slime.bitmap, slime.x.toFloat(), slime.y.toFloat(), paint)

            // Draw mushrooms on top of everything else
            mushrooms.draw(canvas)

            // Restore the canvas state
            canvas.restore()

            // Unlock and post the canvas
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }


    private fun controlFrameRate() {
        val targetFPS = 60
        val targetTime = 1_000_000_000L / targetFPS
        val startTime = System.nanoTime()

        val timeTaken = System.nanoTime() - startTime
        val sleepTime = targetTime - timeTaken

        if (sleepTime > 0) {
            Thread.sleep(sleepTime / 1_000_000L)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            slime.jump()
        }
        return true
    }

    fun pause() {
        playing = false
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun resetGame() {
        slime.x = 100
        slime.y = screenHeight - 100
        slime.isJumping = false
        slime.velocityY = 0
        mushrooms.update()
    }
}