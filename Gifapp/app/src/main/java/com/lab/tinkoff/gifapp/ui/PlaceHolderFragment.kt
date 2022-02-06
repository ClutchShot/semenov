package com.lab.tinkoff.gifapp.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lab.tinkoff.gifapp.databinding.PlaceHolderFragmentBinding
import com.lab.tinkoff.gifapp.repository.PlaceHolderViewModel

class PlaceHolderFragment : Fragment() {

    private lateinit var sharedViewModel: PlaceHolderViewModel
    private lateinit var binding: PlaceHolderFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        sharedViewModel = ViewModelProvider(this).get(PlaceHolderViewModel::class.java)
        val category = arguments?.getString(ARG_CATEGORY)
        sharedViewModel.setCategory(category!!)
        sharedViewModel.getGifs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PlaceHolderFragmentBinding.inflate(inflater,container,false)
        sharedViewModel.responseApi.observe(viewLifecycleOwner,{

            if (it.isSuccessful){
                binding.mainView.visibility = View.VISIBLE
                if (it.body()?.result?.isEmpty()!!){
                    binding.mainView.visibility = View.INVISIBLE
                }
            }
            if (it.code()>300){
                binding.mainView.visibility = View.INVISIBLE

            }
        })

        sharedViewModel.current_gif.observe(viewLifecycleOwner, {
            Glide.with(requireContext())
                .asGif()
                .load(it.gifURL)
                .listener(object : RequestListener<GifDrawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressCircular.visibility = View.INVISIBLE
                        return false
                    }

                })
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.imgGif)

            binding.textDesc.text = it.description
        })


        sharedViewModel.current_gif_index.observe(viewLifecycleOwner,{
            binding.btnPrevious.isEnabled = it != 0
        })


        val btnNext = binding.btnNext
        btnNext.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE
            sharedViewModel.nextGif()
        }

        val btnPrev = binding.btnPrevious
        btnPrev.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE
            sharedViewModel.prevGif()
        }

        val btnAgain = binding.errorView.btnAgain
        btnAgain.setOnClickListener {
            sharedViewModel.getGifs()
            binding.mainView.visibility = View.VISIBLE
        }

        return binding.root
    }



    companion object {
        private const val ARG_CATEGORY = "section_number"

        @JvmStatic
        fun newInstance(section: String) = PlaceHolderFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CATEGORY, section)
            }
        }
    }

}