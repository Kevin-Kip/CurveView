package com.revosleap.curveview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CurveView : View {

    private lateinit var curvePaint: Paint
    private lateinit var borderPaint: Paint
    private var borderWidth: Float = 1F
    private var borderColor: Int = Color.WHITE
    private var curveColor: Int = Color.BLUE
    private var curveDirection = CurveDirection.TOP

    constructor(context: Context?) : super(context) {
        init(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CurveView, defStyle, 0)
        borderColor = typedArray.getColor(R.styleable.CurveView_cv_border_color, borderColor)
        borderWidth = typedArray.getDimension(R.styleable.CurveView_cv_border_width, borderWidth)
        curveDirection = typedArray.getInt(R.styleable.CurveView_cv_curve_direction, CurveDirection.TOP)
        curveColor = typedArray.getColor(R.styleable.CurveView_cv_background_color, curveColor)
        typedArray.recycle()

        curvePaint = Paint().apply {
            color = curveColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        borderPaint = Paint().apply {
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            isAntiAlias = true
        }
    }

    private fun invalidateResources() {
        curvePaint.color = curveColor
        borderPaint.color = borderColor
        borderPaint.strokeWidth = borderWidth
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        val w = width
        val h = height

        val contentWidth = w - paddingLeft - paddingRight
        val contentHeight = h - paddingTop - paddingBottom

        val widthSquare = Math.pow(contentWidth.toDouble(), 2.0)
        val heightSquare = Math.pow(contentHeight.toDouble(), 2.0)
        val radius = (((widthSquare / 0.6) + heightSquare) / (2.0 * contentHeight)).toFloat()

        when (curveDirection) {
            CurveDirection.TOP -> {
                canvas!!.drawCircle(contentWidth / 2.0F, radius, radius, curvePaint)
                canvas.drawCircle(contentWidth / 2.0F, radius, radius - (borderWidth / 2.0F), borderPaint)
            }
            CurveDirection.BOTTOM -> {
                canvas!!.drawCircle(contentWidth / 2.0f, -(radius - contentHeight), radius, curvePaint)
                canvas.drawCircle(contentWidth / 2.0f, -(radius - contentHeight), radius - (borderWidth / 2.0f), borderPaint)
            }
            else -> {
                canvas!!.drawCircle(contentWidth / 2.0f, radius, radius, curvePaint)
                canvas.drawCircle(contentWidth / 2.0f, radius, radius - (borderWidth / 2.0f), borderPaint)
            }
        }
    }

    fun getBorderColor(): Int {
        return borderColor
    }

    fun setBorderColor(borderColor: Int) {
        this.borderColor = borderColor
        invalidateResources()
    }

    fun getCurveDirection(): Int {
        return curveDirection
    }

    fun setCurveDirection(curveDirection: Int) {
        this.curveDirection = curveDirection
        invalidateResources()
    }

    fun getBorderWidth(): Float {
        return borderWidth
    }

    fun setBorderWidth(borderWidth: Float) {
        this.borderWidth = borderWidth
        invalidateResources()
    }

    fun getBackgroundColor(): Int {
        return curveColor
    }

    override fun setBackgroundColor(curveColor: Int) {
        this.curveColor = curveColor
        invalidateResources()
    }
}