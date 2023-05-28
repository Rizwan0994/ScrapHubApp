package com.example.scrapproject.collectorInterface

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.scrapproject.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*


class GernateQRFragment : Fragment() {

    private lateinit var qrImageView: ImageView
    private lateinit  var contactEditText:EditText
    private lateinit  var nearestYardEditText:EditText
    private lateinit  var sDateEditText:EditText
    private lateinit  var sTimeEditText:EditText
    private lateinit  var itemDetailsEditText:EditText
    private lateinit  var  collectorYardEditText:EditText
    private lateinit  var addressEditText:EditText
    private lateinit  var collectorIdEditText:EditText
    private lateinit var generateButton: Button
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gernate_q_r, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactEditText=view.findViewById(R.id.contactEditText)
        addressEditText=view.findViewById(R.id.addressEditText)
        nearestYardEditText=view.findViewById(R.id.nearestYardEditText)
        sDateEditText=view.findViewById(R.id.sDateEditText)
        sTimeEditText=view.findViewById(R.id.sTimeEditText)
        itemDetailsEditText=view.findViewById(R.id.txtScrapItemDetails)
        collectorYardEditText=view.findViewById(R.id.collectorYardEditText)
        collectorIdEditText=view.findViewById(R.id.collectorIDEditText)
        generateButton=view.findViewById(R.id.generateButton)
        saveButton=view.findViewById(R.id.saveButton);
        qrImageView=view.findViewById(R.id.imageView)


        generateButton.setOnClickListener {
            val text = generateQRCodeText()
            if (text.isEmpty()) {
                return@setOnClickListener
            }
            generateQRCode(text)
        }



    }

    private fun generateQRCodeText(): String {
        val contact=contactEditText.text.toString().trim()
        val address = addressEditText.text.toString().trim()
        val nearestYard = nearestYardEditText.text.toString().trim()
        val sDate = sDateEditText.text.toString().trim()
        val sTime = sTimeEditText.text.toString().trim()
        val itemDetails = itemDetailsEditText.text.toString().trim()
        val collectorYard = collectorYardEditText.text.toString().trim()
        val collectorId = collectorIdEditText.text.toString().trim()


        return """
            Contact:$contact
            Address: $address
            Nearest Yard: $nearestYard
            Date: $sDate
            Time: $sTime
            Item Details: $itemDetails
            Collector Yard: $collectorYard
            Collector ID: $collectorId
        """.trimIndent()
    }

    private fun generateQRCode(text: String) {
        val writer = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
                }
            }
            qrImageView.setImageBitmap(bitmap)

            saveButton.setOnClickListener {
                val bitmap = (qrImageView.drawable as BitmapDrawable).bitmap
                val filename = "${UUID.randomUUID()}.png"
                val stream = context?.openFileOutput(filename, Context.MODE_PRIVATE)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream?.close()
                Toast.makeText(context, "QR code saved as $filename", Toast.LENGTH_SHORT).show()
            }
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }




}