package tk.zwander.commonCompose.view.pager

import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import tk.zwander.commonCompose.view.components.Page

@Composable
expect fun HorizontalPager(
    count: Int,
    currentPage: Int,
    onPageChanged: (Int) -> Unit,
    eval: @Composable (Page) -> Unit,
): (@Composable (List<TabPosition>) -> Unit)?
