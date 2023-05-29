package com.stathis.core.ext

import android.app.Activity
import android.graphics.Bitmap
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.stathis.core.R

/**
 * Helper method to set the screen title inside a [Fragment] in a more simple way.
 */

fun Fragment.setScreenTitle(title: String) {
    requireActivity().title = title
}

/**
 * Helper fun to simplify the addition of a custom menu inside a [Fragment].
 */

fun Fragment.addAppBarMenu(menuId: Int, selectedOption: (Int) -> Unit) {
    val menuHost: MenuHost = requireActivity()

    menuHost.addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            // Add menu items here
            menuInflater.inflate(menuId, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            selectedOption.invoke(menuItem.itemId)
            return true
        }
    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
}

/**
 * Helper fun to add a search bar action bar and get callback.
 */
fun Fragment.addSearchBarMenu(menuId: Int, callback: (String) -> Unit) {
    val menuHost: MenuHost = requireActivity()
    menuHost.addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuId, menu)
            val item: MenuItem? = menu.findItem(R.id.search)
            val searchView = item?.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    callback.invoke(query.toString())
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    callback.invoke(newText.toString())
                    return false
                }
            })
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return true
        }
    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
}

/**
 * Helper fun to simplify the activity result api for Camera purposes.
 */

fun Fragment.onSuccessCameraResult(data: (Bitmap?) -> Unit) = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
        val bitmap = result.data?.extras?.get("data") as? Bitmap
        data.invoke(bitmap)
    }
}

fun Fragment.askUserForAction(title: String, btnTitle: String, onPrimaryBtnClick: () -> Unit) {
    requireContext().askUserForAction(title, btnTitle, onPrimaryBtnClick)
}