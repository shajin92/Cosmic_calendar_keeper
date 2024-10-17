package com.sendil.cosmiccalenderkeeper.navigation

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sendil.cosmiccalenderkeeper.R
import com.sendil.cosmiccalenderkeeper.db.AppDb
import com.sendil.cosmiccalenderkeeper.models.EventsItem
import com.sendil.cosmiccalenderkeeper.models.MyEvents
import com.sendil.cosmiccalenderkeeper.repository.EventsRepository
import com.sendil.cosmiccalenderkeeper.viewmodels.EventDbViewModel
import com.sendil.cosmiccalenderkeeper.viewmodels.EventInfoViewModel
import com.sendil.cosmiccalenderkeeper.viewmodels.EventsDbViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val navItemList = listOf(
        NavItem("Total Events", Icons.Default.Home),
        NavItem("My Events", Icons.Default.Notifications)
    )

    var bottomIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = bottomIndex == index,
                        onClick = {
                            bottomIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = "Icon")
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), bottomIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    val context = LocalContext.current
    val userDao = AppDb.getDatabase(context).eventDao()
    val userRepository = EventsRepository(userDao)

    val owner = LocalLifecycleOwner.current as ViewModelStoreOwner

    val factory = remember { EventsDbViewModelFactory(userRepository) }
    val userViewModel: EventDbViewModel = viewModel(factory = factory, viewModelStoreOwner = owner)
    val backgroundPainter = painterResource(id = R.drawable.bg_space)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = backgroundPainter,
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Adjust scale based on your requirement
        )

        when (selectedIndex) {
            0 -> TotalEvents(userViewModel)
            1 -> MyEvents(userViewModel, modifier)
        }
    }
}




@Composable
fun TotalEvents(userViewModel: EventDbViewModel) {

    val viewModel: EventInfoViewModel = viewModel()
    val context = LocalContext.current
    viewModel.getTotalEvents(context)

    val data by viewModel.events.collectAsState(initial = null)
    val monthsList = listOf(
        "All Months", "January",
        "February", "March",
        "April", "May", "June", "July", "August", "September", "October", "November", "December"
    )
    val eventTypeList = listOf(
        "All Event Types",
        "Meteor Showers",
        "Moon Events",
        "Planet Events",
        "Other Events"
    )
    var selectedMonth by remember { mutableStateOf(monthsList[0]) }
    var selectedEventType by remember { mutableStateOf(eventTypeList[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)

        ) {
            Text(
                text = "2024 Celestial Events Tracker",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color(0xffC084FC),
                lineHeight = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Your comprehensive guide to the cosmic calendar",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color(0XFFE9D8FD),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                DropdownMenu(
                    title = "Months",
                    list = monthsList,
                    defaultValue = monthsList[0],
                    selectedMonth = {
                        selectedMonth = it
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                DropdownMenu(
                    title = "Event Type",
                    list = eventTypeList,
                    defaultValue = eventTypeList[0],
                    selectedMonth = {
                        selectedEventType = it
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        data?.let { event ->
            if (event.isNotEmpty()) {
                if (selectedMonth != "All Months") {
                    val filteredMonth = event.filter {
                        it.month.equals(selectedMonth, ignoreCase = true)
                    }

                    if (selectedEventType != eventTypeList[0]) {
                        val eventList = filteredMonth.filter {
                            it.type.contains(
                                selectedEventType.split(" ").first(),
                                ignoreCase = true
                            )
                        }
                        if (eventList.isNotEmpty()) {
                            ListInfo(userViewModel,eventList)
                        } else {
                            Text(text = "No Data found")
                        }
                    } else {
                        if (filteredMonth.isNotEmpty()) {
                            ListInfo(userViewModel,filteredMonth)
                        } else {
                            Text(text = "No Data found")
                        }
                    }
                } else {
                    if (selectedEventType != eventTypeList[0]) {
                        val filterList = event.filter {
                            it.type.contains(
                                selectedEventType.split(" ").first(),
                                ignoreCase = true
                            )
                        }
                        if (filterList.isNotEmpty()) {
                            ListInfo(userViewModel,filterList)
                        } else {
                            Text(text = "No Data found")
                        }
                    } else {
                        ListInfo(userViewModel,event)
                    }
                }
            } else {
                Text(text = "No Data found")
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    title: String,
    list: List<String>,
    defaultValue: String,
    selectedMonth: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(defaultValue) }

    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                readOnly = true,
                label = { Text(text = title, color = Color(0xFFFFFFFF)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFFFFFFF),
                    unfocusedBorderColor = Color(0xFFFFFFFF),
                    focusedTextColor = Color(0xFFFFFFFF),
                    unfocusedTextColor = Color(0xFFFFFFFF)
                )

            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                list.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOption = selectionOption
                            selectedMonth(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun MyEvents(userViewModel: EventDbViewModel,modifier: Modifier = Modifier) {

    val users = userViewModel.allUsers.observeAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (users.value.isNotEmpty()){
            val myEvents:MutableList<EventsItem> = mutableListOf()

            for (i in users.value){
                val eventItem = EventsItem(
                    date = i.date,
                    month = i.month,
                    title = i.title,
                    type = i.type
                )
                myEvents.add(eventItem)
            }
            ListInfo(userViewModel,myEvents, isMyEvent = false)
        }
        else{
            Text(text = "No Data found")
        }
    }
}


fun getMilliseconds(month: String, day: Int): Long {

    val dateFormat = SimpleDateFormat("MMMM d", Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getDefault()
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val parsedDate = dateFormat.parse("$month $day")
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    if (parsedDate != null) {
        calendar.time = parsedDate
    }
    calendar.set(Calendar.YEAR, currentYear)
    return calendar.timeInMillis
}

@Composable
fun CalendarItem(
    event: EventsItem,
    backgroundColor: Color,
    isMyEvent: Boolean,
    userViewModel: EventDbViewModel
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = event.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (event.type == "meteor")
                    Color(0xFF43D18D)
                else if (event.type == "moon")
                    Color(0xFF93c5fd)
                else if (event.type == "planet")
                    Color(0xFF6EE7B7)
                else
                    Color(0xFFfcd34d),
                modifier = if (isMyEvent) {
                    Modifier.width(150.dp)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            Text(text = "${event.month} ${event.date}", fontSize = 14.sp, color = Color(0xFFCCCCCC))
        }
        if (isMyEvent){
            Button(
                onClick = {
                    val dbEvent = MyEvents(
                        date = event.date,
                        title = event.title,
                        month = event.month,
                        type = event.type
                    )
                    userViewModel.insert(dbEvent)

                    val i = Intent(Intent.ACTION_EDIT)
                    i.setType("vnd.android.cursor.item/event")
                    i.putExtra("beginTime", getMilliseconds(event.month, event.date.toInt()))
                    i.putExtra("allDay", false)
                    i.putExtra("rule", "FREQ=DAILY")
                    i.putExtra("endTime", getMilliseconds(event.month, event.date.toInt()) + 86400000)
                    i.putExtra("title", event.title)
                    context.startActivity(i)
                },
                shape = RoundedCornerShape(8.dp),
                    colors = ButtonColors(
                        containerColor = Color(0xff8B5CF6),
                        contentColor = Color(0xffffffff),
                        disabledContentColor = Color(0xffffffff),
                        disabledContainerColor = Color(0xff8B5CF6)
                    )

            ) {
                Text(text = "Add to Calendar")
            }
        }

    }
}


@Composable
fun ListInfo(userViewModel:EventDbViewModel,data: List<EventsItem>, modifier: Modifier = Modifier,isMyEvent :Boolean = true) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {
        items(data.size) { index ->
            CalendarItem(
                data[index],
                backgroundColor = Color(0xFF2A2D46),
                isMyEvent,
                userViewModel
            )
        }
    }
}






