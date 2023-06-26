package com.stathis.core.ext

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.stathis.core.R
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

            lifecycleScope.launch {
                searchView.getQueryTextChangeStateFlow()
                    .debounce(500)
                    .distinctUntilChanged()
                    .collectLatest {
                        callback.invoke(it)
                    }
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return true
        }
    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
}

private fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
    val flow = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            flow.value = query.toString()
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            flow.value = newText
            return true
        }
    })

    return flow
}

/**
 * Helper method to set a custom Menu inside a Fragment.
 * @param menuId : The menu id reference that will be inflated.
 * @param onMenuCreated : Callback in case the user needs a reference to the menu
 * @param onItemSelected : Callback for user clicks on menu items.
 */

fun Fragment.setMenuProvider(
    menuId: Int,
    onMenuCreated: (Menu) -> Unit,
    onItemSelected: (MenuItem) -> Unit
) {
    requireActivity().addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuId, menu)
            onMenuCreated.invoke(menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            onItemSelected.invoke(menuItem)
            return false
        }
    }, viewLifecycleOwner)
}

/**
 * Helper method to simplify the procedure of getting a drawable inside a fragment.
 */

fun Fragment.getDrawable(drawableId: Int): Drawable? {
    return requireContext().getAppDrawable(drawableId)
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

fun Fragment.onSuccessResult(data: (ActivityResult) -> Unit) = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
        data.invoke(result)
    }
}

fun Fragment.askUserForAction(title: String, btnTitle: String, onPrimaryBtnClick: () -> Unit) {
    requireContext().askUserForAction(title, btnTitle, onPrimaryBtnClick)
}

fun Fragment.hideKeyboard() {
    requireView().hideKeyboard()
}