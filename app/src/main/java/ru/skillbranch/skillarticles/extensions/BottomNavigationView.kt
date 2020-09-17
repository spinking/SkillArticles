package ru.skillbranch.skillarticles.extensions

import androidx.core.view.iterator
import androidx.navigation.NavDestination
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.skillbranch.skillarticles.R

fun BottomNavigationView.selectDestination(destination: NavDestination) {
    val item = this.menu.findItem(destination.id)

    if(item != null)
        item.isChecked = true
    else {
        this.menu.findItem(R.id.nav_profile).isChecked = true
    }
}

fun BottomNavigationView.selectItem(itemId: Int?){
    itemId?: return
    for (item in menu.iterator()) {
        if(item.itemId == itemId) {
            item.isChecked = true
            break
        }
    }
}