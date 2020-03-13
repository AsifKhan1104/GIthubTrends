package com.example.githubtrending.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.githubtrending.R
import com.example.githubtrending.models.RepoResponse
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class RepoDetailActivity : AppCompatActivity() {
    var btnVisit: Button? = null
    var imageViewAvatar: CircleImageView? = null
    var imageViewLangColor: ImageView? = null
    var textViewCurrPeriodStars: TextView? = null
    var textViewForks: TextView? = null
    var textViewLang: TextView? = null
    var textViewStars: TextView? = null
    var textViewTitle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)

        initView()
    }

    private fun initView() {
        btnVisit = findViewById(R.id.btn_visit)
        imageViewAvatar = findViewById(R.id.item_profile_img)
        imageViewLangColor = findViewById(R.id.item_img_language)
        textViewCurrPeriodStars = findViewById(R.id.item_curr_stars)
        textViewForks = findViewById(R.id.item_forks)
        textViewLang = findViewById(R.id.item_language)
        textViewStars = findViewById(R.id.item_stars)
        textViewTitle = findViewById(R.id.item_title)

        setData()
    }

    private fun setData() {
        val data: RepoResponse = intent.getParcelableExtra("repoList")
        textViewTitle?.setText(data.name)
        if (data.language != null) {
            val language = data.language
            textViewLang?.setText(language)
        }
        textViewStars?.setText(data.stars.toString())
        textViewCurrPeriodStars?.setText(data.currentPeriodStars.toString())
        textViewForks?.setText(data.forks.toString())

        // set avatar
        Picasso.get().load(data.avatar).into(imageViewAvatar)
        // set language color
        if (data.language != null) {
            val drawable =
                getResources().getDrawable(R.drawable.ic_circle) as GradientDrawable
            drawable.setColor(Color.parseColor(data.languageColor))
            imageViewLangColor?.setImageDrawable(drawable)
        } else {
            textViewLang?.visibility = View.GONE
            imageViewLangColor?.visibility = View.GONE
        }

        // visit site
        btnVisit?.setOnClickListener(View.OnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
            startActivity(browserIntent)
        })
    }
}
