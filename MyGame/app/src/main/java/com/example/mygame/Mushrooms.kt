package com.example.mygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import kotlin.random.Random

class Mushrooms(context: Context, private val maxX: Int, private val maxY: Int) {

    private val scaleFactor = 0.75f // Scale mushrooms to 50% of their original size
    private val mushroomImages: List<Bitmap> = loadMushroomImages(context)
    private val mushrooms = mutableListOf<Pair<Rect, Int>>() // Pair of Rect and image index
    private val mushroomWidth = (mushroomImages[0].width * scaleFactor).toInt()
    private val mushroomHeight = (mushroomImages[0].height * scaleFactor).toInt()
    private val mushroomSpeed = 20
    private val minSpacing = 3000
    private val maxSpacing = 5600
    private val minDistanceBetweenMushrooms = 600 // Minimum distance between mushrooms
    private val paint = Paint()

    // Paint object for the mushroom hitbox
    private val hitboxPaint: Paint = Paint().apply {
        color = Color.GREEN // Color of the hitbox rectangle for mushrooms
        strokeWidth = 15f // Line thickness
        style = Paint.Style.STROKE // Only draw the border, not filled
    }

    // Pool size and maximum active mushrooms
    private val poolSize = 3 // Use 3 mushrooms
    private var nextSpawnX = maxX

    init {
        // Initialize the pool with 3 mushrooms
        repeat(poolSize) {
            addMushroomToPool()
        }
    }

    private fun loadMushroomImages(context: Context): List<Bitmap> {
        return listOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.mushroom1),
            BitmapFactory.decodeResource(context.resources, R.drawable.mushroom2),
            BitmapFactory.decodeResource(context.resources, R.drawable.mushroom3)
        ).map { mushroom ->
            Bitmap.createScaledBitmap(
                mushroom,
                (mushroom.width * scaleFactor).toInt(),
                (mushroom.height * scaleFactor).toInt(),
                false
            )
        }
    }

    private fun addMushroomToPool() {
        val randomImageIndex = Random.nextInt(0, mushroomImages.size) // Choose a random mushroom image
        val spawnX = generateNonOverlappingX() // Generate a valid non-overlapping spawn position
        val spawnY = maxY - mushroomHeight // Align to the floor
        val mushroomRect = Rect(spawnX, spawnY, spawnX + mushroomWidth, spawnY + mushroomHeight)
        mushrooms.add(Pair(mushroomRect, randomImageIndex))
    }

    private fun generateNonOverlappingX(): Int {
        val spawnX = maxX + Random.nextInt(minSpacing, maxSpacing)
        for ((rect, _) in mushrooms) {
            if (spawnX in rect.left - minDistanceBetweenMushrooms..rect.right + minDistanceBetweenMushrooms) {
                // Adjust spawnX to ensure no overlap
                return rect.right + minDistanceBetweenMushrooms
            }
        }
        return spawnX
    }

    fun update() {
        val iterator = mushrooms.iterator()
        while (iterator.hasNext()) {
            val (rect, _) = iterator.next()

            // Move the mushroom to the left
            rect.offset(-mushroomSpeed, 0)

            // If a mushroom moves off-screen, recycle it
            if (rect.right < 0) {
                recycleMushroom(rect)
            }
        }
    }

    private fun recycleMushroom(rect: Rect) {
        val spawnX = generateNonOverlappingX()
        rect.left = spawnX
        rect.right = rect.left + mushroomWidth
        rect.top = maxY - mushroomHeight
        rect.bottom = rect.top + mushroomHeight

        // Assign a new random image index
        mushrooms[mushrooms.indexOfFirst { it.first == rect }] = Pair(rect, Random.nextInt(mushroomImages.size))
    }

    fun draw(canvas: Canvas) {
        for ((mushroomRect, imageIndex) in mushrooms) {
            drawMushroom(canvas, mushroomRect, imageIndex)
            // Draw the hitbox around the mushroom
            // canvas.drawRect(mushroomRect, hitboxPaint)
        }
    }

    private fun drawMushroom(canvas: Canvas, mushroomRect: Rect, imageIndex: Int) {
        canvas.drawBitmap(mushroomImages[imageIndex], null, mushroomRect, paint)
    }

    fun checkCollision(slime: Slime): Boolean {
        if (!slime.isAlive) return false

        for ((mushroomRect, _) in mushrooms) {
            if (Rect.intersects(slime.detectCollision, mushroomRect)) {
                slime.die()
                return true
            }
        }
        return false
    }
}