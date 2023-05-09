package com.sultandev.evtest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sultandev.evtest.R
import com.sultandev.evtest.data.model.Article
import com.sultandev.evtest.databinding.ItemNewsBinding
import com.sultandev.evtest.utils.toPublishDate

class PhotosAdapter : ListAdapter<Article, PhotosAdapter.ViewHolder>(PhotoAdapterCompressor) {

    private var onClickListener: ((String) -> Unit)? = null

    fun setOnClickListener(block: (String) -> Unit) {
        onClickListener = block
    }

    var url = ""

    inner class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {

            var stateElement = false

            binding.apply {

                tvTitle.text = getItem(absoluteAdapterPosition).title
                tvDescriptionDetail.text = getItem(absoluteAdapterPosition).description
                tvDate.text = getItem(absoluteAdapterPosition).publishedAt.toPublishDate()


                iconMore.setImageResource(R.drawable.caret_down)
                tvDescriptionDetail.isVisible = false

                Glide
                    .with(binding.root.context)
                    .load(getItem(absoluteAdapterPosition).urlToImage)
                    .into(binding.ivNew)

                iconMore.setOnClickListener {
                    if(stateElement) {
                        tvDescriptionDetail.isVisible = false
                        iconMore.setImageResource(R.drawable.caret_down)
                        stateElement = false
                    } else {
                        tvDescriptionDetail.isVisible = true
                        iconMore.setImageResource(R.drawable.caret_up)
                        stateElement = true
                    }
                }

                url = getItem(absoluteAdapterPosition).url
                binding.root.setOnClickListener {
                    onClickListener?.invoke(url)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()

}

object PhotoAdapterCompressor : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.author == newItem.author &&
                oldItem.content == newItem.content &&
                oldItem.description == newItem.description &&
                oldItem.publishedAt == newItem.publishedAt &&
                oldItem.title == newItem.title &&
                oldItem.url == newItem.url
    }
}