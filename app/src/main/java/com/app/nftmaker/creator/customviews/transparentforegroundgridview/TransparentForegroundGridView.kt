package nft.nftcreator.pixel.pixelart.creator.customviews.transparentbackgroundview

import android.R
import android.R.attr
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


class PixelGridView(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private var numColumns = 0
    private var numRows = 0
    private var cellWidth = 0
    private var cellHeight = 0
    private val blackPaint: Paint = Paint()
    private lateinit var cellChecked: Array<BooleanArray>
    fun setNumColumns(numColumns: Int) {
        this.numColumns = numColumns
        calculateDimensions()
    }

    fun getNumColumns(): Int {
        return numColumns
    }

    fun setNumRows(numRows: Int) {
        this.numRows = numRows
        calculateDimensions()
    }

    fun getNumRows(): Int {
        return numRows
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateDimensions()
    }

    private fun calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return
        }
        cellWidth = width / numColumns
        cellHeight = height / numRows
        cellChecked = Array(numColumns) { BooleanArray(numRows) }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)

        if (numColumns == 0 || numRows == 0) {
            return
        }
        val width = width
        val height = height
        for (i in 0 until numColumns) {
            for (j in 0 until numRows) {
                if (cellChecked[i][j]) {
                    canvas.drawRect(
                        (i * cellWidth).toFloat(), (j * cellHeight).toFloat(), (
                                (i + 1) * cellWidth).toFloat(), ((j + 1) * cellHeight).toFloat(),
                        blackPaint
                    )
                }
            }
        }
        for (i in 1 until numColumns) {
            canvas.drawLine(
                (i * cellWidth).toFloat(),
                0f,
                (i * cellWidth).toFloat(),
                height.toFloat(),
                blackPaint
            )
        }
        for (i in 1 until numRows) {
            canvas.drawLine(
                0f,
                (i * cellHeight).toFloat(),
                width.toFloat(),
                (i * cellHeight).toFloat(),
                blackPaint
            )
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val column = (event.x / cellWidth).toInt()
            val row = (event.y / cellHeight).toInt()
            cellChecked[column][row] = !cellChecked[column][row]
            invalidate()
        }
        return false
    }

    init {
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE)
    }
}


class PixelGridViewx(context: Context, val horizontalGridCount: Int, attrs: AttributeSet? = null) :
    View(context, attrs) {
    //number of row and column
    private val horiz: Drawable
    private val vert: Drawable
    private val width: Float
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        horiz.setBounds(left, 0, right, width.toInt())
        vert.setBounds(0, top, width.toInt(), bottom)
    }

    private fun getLinePosition(lineNumber: Int): Float {
        val lineCount = horizontalGridCount
        return 1f / (lineCount + 1) * (lineNumber + 1f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // drawTask.start();
        val count = horizontalGridCount
        for (n in 0 until count) {
            val pos = getLinePosition(n)

            // Draw horizontal line
            canvas.translate(0f, pos * height)
            horiz.draw(canvas)
            canvas.translate(0f, -pos * height)

            // Draw vertical line
            canvas.translate(pos * getWidth(), 0f)
            vert.draw(canvas)
            canvas.translate(-pos * getWidth(), 0f)
        }
        //drawTask.end(count);
    }

    init {
        horiz = ColorDrawable(Color.WHITE)
        horiz.alpha = 160
        vert = ColorDrawable(Color.WHITE)
        vert.alpha = 160
        width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            0.9f,
            context.resources.displayMetrics
        )
    }
}


/**
 * GridLines is a view which directly overlays the preview and draws
 * evenly spaced grid lines.
 */
class GridLines(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private var mDrawBounds: RectF? = null
    var mPaint = Paint()
    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mDrawBounds != null) {
            val thirdWidth = mDrawBounds!!.width() / 3
            val thirdHeight = mDrawBounds!!.height() / 3
            for (i in 1..2) {
                // Draw the vertical lines.
                val x = thirdWidth * i
                canvas.drawLine(
                    mDrawBounds!!.left + x, mDrawBounds!!.top,
                    mDrawBounds!!.left + x, mDrawBounds!!.bottom, mPaint
                )
                // Draw the horizontal lines.
                val y = thirdHeight * i
                canvas.drawLine(
                    mDrawBounds!!.left, mDrawBounds!!.top + y,
                    mDrawBounds!!.right, mDrawBounds!!.top + y, mPaint
                )
            }
        }
    }

    fun onPreviewAreaChanged(previewArea: RectF) {
        setDrawBounds(previewArea)
    }

    private fun setDrawBounds(previewArea: RectF) {
        mDrawBounds = RectF(previewArea)
        invalidate()
    }

    init {
//        mPaint.strokeWidth = resources.getDimensionPixelSize(R.dimen.grid_line_width).toFloat()
        mPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            0.9f,
            context?.resources?.displayMetrics
        )
        mPaint.color = resources.getColor(R.color.white)
    }
}
