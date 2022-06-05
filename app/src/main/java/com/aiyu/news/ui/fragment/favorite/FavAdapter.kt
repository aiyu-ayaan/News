package com.aiyu.news.ui.fragment.favorite


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aiyu.core.models.Article
import com.aiyu.news.R
import com.aiyu.news.databinding.RowNewsBinding
import com.aiyu.news.ui.fragment.home.DiffUtilArticle
import com.aiyu.news.ui.utils.convertDateToTime
import com.aiyu.news.ui.utils.loadImage
import java.text.SimpleDateFormat

class FavAdapter(
    private val listener: (Article) -> Unit,
    private val shareClick: (String, String) -> Unit,
    private val longClick: () -> Unit,
    private val onMenuActive: (Article, CardView) -> Unit
) : ListAdapter<Article, FavAdapter.HomeAdapterViewHolder>(DiffUtilArticle()) {

    private var isMenuActive: Boolean = false

    fun setIsMenuActive(isMenuActive: Boolean) {
        this.isMenuActive = isMenuActive
    }

    inner class HomeAdapterViewHolder(
        private val binding: RowNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    getItem(absoluteAdapterPosition)?.let {
                        if (isMenuActive) {
                            onMenuActive.invoke(it, binding.root)

                        } else {
                            listener.invoke(it)
                        }
                    }
            }
            binding.root.setOnLongClickListener {
                isMenuActive = true
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    getItem(absoluteAdapterPosition)?.let {
                        longClick.invoke()
                        onMenuActive.invoke(it, binding.root)
                    }
                true
            }
            binding.imageButtonShare.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    getItem(absoluteAdapterPosition)?.let {
                        shareClick.invoke(it.title, it.url.toString())
                    }
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(article: Article) = binding.apply {
            binding.root.transitionName = article.title
            textViewTextTitle.text = article.title

            article.urlToImage.loadImage(
                itemView,
                imageViewTitleImage,
                progressBarNews,
                10
            )
            imageButtonFav.setImageResource(R.drawable.ic_favorite_bottom_nav)
            try {
                val date =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(article.publishedAt!!)
                        ?.convertDateToTime()

                textViewSource.text = binding.root.context.getString(
                    R.string.author_with_date, article.source?.name, date
                )
            } catch (e: Exception) {
                Toast.makeText(binding.root.context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder =
        HomeAdapterViewHolder(
            RowNewsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}