package com.example.mygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.graphics.Rect

class Slime(context: Context, width: Int, height: Int) {
    var x = 200 // Move slime to the right
    var y = height - 200 // Position on the floor (adjust this value to control height)
    var velocityY = 0 // Vertical velocity
    var isJumping = false
    val jumpStrength = -50 // How high it jumps
    val gravity = 2 // Simulate gravity
    var maxX = width
    var maxY = height
    var minX = 0
    var minY = 0
    var counter = 0

    var sprites: Map<String, Pair<Bitmap, Int>>
    var currentAnimation = "run" // Start with "run" animation
    var detectCollision: Rect
    var currentFrameIndex = 0
    val frameRate = 20
    var isAlive = true

    // New scale factor variable
    val scaleFactor = 1f // Scale the slime to 100% of its original size (can be adjusted)

    init {
        sprites = mapOf(
            "idle" to Pair(BitmapFactory.decodeResource(context.resources, R.drawable.slime_idle), 4),
            "die" to Pair(BitmapFactory.decodeResource(context.resources, R.drawable.slime_die), 5),
            "run" to Pair(BitmapFactory.decodeResource(context.resources, R.drawable.slime_run), 6)
        )

        val initialBitmap = getBitMapFrame(0)
        maxX = width
        maxY = height - (initialBitmap.height * scaleFactor).toInt()
        minX = 0
        minY = 0

        // Set the initial height (y-position) of the slime. Adjust this value as needed.
        y = maxY - 200 // Slime positioned 100 pixels above the bottom (ground level)

        // Update collision rect to reflect new position
        detectCollision = Rect(x, y, x + initialBitmap.width, y + initialBitmap.height)
    }

    val bitmap: Bitmap
        get() {
            counter++
            if (counter % frameRate == 0) {
                currentFrameIndex = (currentFrameIndex + 1) % (sprites[currentAnimation]?.second ?: 1)
            }
            return getBitMapFrame(currentFrameIndex)
        }

    fun getBitMapFrame(frame: Int): Bitmap {
        val (spriteBitmap, frameCount) = sprites[currentAnimation]!!

        val frameWidth = (spriteBitmap.width / frameCount * scaleFactor).toInt()
        val frameHeight = (spriteBitmap.height * scaleFactor).toInt()

        // Ensure the frame is within bounds of the sprite sheet
        val frameWidthInBitmap = spriteBitmap.width / frameCount
        val currentFrame = if (frame * frameWidthInBitmap + frameWidthInBitmap <= spriteBitmap.width) {
            Bitmap.createBitmap(spriteBitmap, frame * frameWidthInBitmap, 0, frameWidthInBitmap, spriteBitmap.height)
        } else {
            // In case the frame calculation is out of bounds, use the last valid frame
            Bitmap.createBitmap(spriteBitmap, (frameCount - 1) * frameWidthInBitmap, 0, frameWidthInBitmap, spriteBitmap.height)
        }

        return Bitmap.createScaledBitmap(currentFrame, frameWidth, frameHeight, false)
    }

    fun die() {
        isAlive = false
        currentAnimation = "die" // Switch to 'die' animation
        // Additional logic for when the slime dies (e.g., reset game, show game over)
    }

    fun update() {
        if (isAlive) {
            // Usual update logic
            if (isJumping) {
                velocityY += gravity
                y += velocityY
                // Modify landing check to respect the height offset (maxY - 100)
                if (y >= maxY - 200) {  // Ground level with offset
                    y = maxY - 200
                    isJumping = false
                    velocityY = 0
                    currentAnimation = "run" // Switch back to "run" when landing
                }
            }
        }

        // Update the collision rectangle based on the slime’s position and scale
        detectCollision.left = x
        detectCollision.top = y
        detectCollision.right = x + (bitmap.width * scaleFactor).toInt()
        detectCollision.bottom = y + (bitmap.height * scaleFactor).toInt()
    }

    fun jump() {
        if (!isJumping) {
            isJumping = true
            velocityY = jumpStrength
            currentAnimation = "idle" // Switch to "idle" when the slime jumps
        }
    }
}
