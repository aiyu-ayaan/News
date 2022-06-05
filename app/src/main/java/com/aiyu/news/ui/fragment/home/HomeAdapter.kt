package com.aiyu.news.ui.fragment.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aiyu.core.models.Article
import com.aiyu.news.databinding.RowNewsBinding
import com.aiyu.news.ui.utils.loadImage

class HomeAdapter(
    private val listener: (Article) -> Unit
) : PagingDataAdapter<Article, HomeAdapter.HomeAdapterViewHolder>(DiffUtilArticle()) {

    inner class HomeAdapterViewHolder(
        private val binding: RowNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    getItem(absoluteAdapterPosition)?.let {
                        listener.invoke(it)
                    }
            }
        }

        fun bind(article: Article) = binding.apply {
            binding.root.transitionName = article.title
            textViewTextTitle.text = article.title
            textViewSource.text = article.source?.name
            article.urlToImage.loadImage(
                itemView,
                imageViewTitleImage,
                progressBarNews,
                10
            )
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

class DiffUtilArticle() : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem

}