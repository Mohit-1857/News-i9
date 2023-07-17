package com.example.test.Home

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.*
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.test.Credentials.ViewModel.FAuthViewModel
import com.example.test.DataModel.News
import com.example.test.DetailNews.DetailArticle
import com.example.test.DetailNews.NewsWebView
import com.example.test.R
import com.example.test.Read_Later.FIrebaseDatabaseViewModel
import com.example.test.ui.theme.TestTheme
import javax.inject.Inject

class Home
    @Inject
    constructor()
    : Screen {

    var newsListResponseC : List<News> by mutableStateOf(listOf())

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TestTheme {
            Surface(
            ) {
                Scaffold(
                    bottomBar = {
                        Bar()
                    }
                ) {

                }
            }
        }



        val navigator = LocalNavigator.currentOrThrow



        Column(
            modifier = Modifier.padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
           // NewsCall("general")
        }

    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Bar(

    ){
        Scaffold(){
            TabNavigator(HomeTab) {
                Scaffold(
                    content = {
                        CurrentTab()
                    },
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = Color(0xFF0172d9)
                        ) {
                            TabNavigationItem(HomeTab)
                            TabNavigationItem(tab = SearchTab)
                            TabNavigationItem(ProfileTab)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        BottomNavigationItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = { tab.icon?.let { Icon(painter = it, contentDescription = tab.title) } })
    }

    object HomeTab : Tab{

        var newsListResponseC : List<News> by mutableStateOf(listOf())
        val categoryList = arrayOf("general" , "entertainment" , "business" , "health" , "science" , "sports" , "technology")
        var category by mutableStateOf("general")
//        var newss : News = TODO()
//        var detailS : DetailS = DetailS(newss)

        override val options: TabOptions
            @Composable
            get()  {
                val title = "Home"
                val icon = rememberVectorPainter(Icons.Default.Home)

                return remember {
                    TabOptions(
                        0u,
                        title, icon
                    )
                }
            }

        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        override fun Content() {
            TestTheme {
                Surface(
                ) {
                    Scaffold(
                        topBar = {
                            RowRecyclerView()
                        }
                    ) {
                        NewsCall(category)
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))



        }

        @Composable
        fun RowItem(categoryC : String ,  newsViewModel: NewsViewModel = hiltViewModel()){
            Row(

            ) {
                Spacer(modifier = Modifier.width(5.dp))


                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(Color(0xFF0172d9)),
                    onClick = {

//                    if(newsViewModel.listResponse.isEmpty()){
//                        Log.d("Error" , newsViewModel.errorMessage)
//                    }
                        category = categoryC

                        newsViewModel.getNewss(category)
                        newsListResponseC = newsViewModel.listResponse


                    }
                ) {

                    //NewsCall(category = category)
                    Text(text = categoryC)
                    Spacer(modifier = Modifier.height(15.dp))

                    //
                }

                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        @SuppressLint("UnrememberedMutableState")
        @Composable
        fun RowRecyclerView(){
            // val categoryList = arrayOf("general" , "entertainment" , "business" , "health" , "science" , "sports" , "technology")
            LazyRow(

            ){
                itemsIndexed(items = categoryList){
                        index, item ->

                    RowItem(item )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun NewsItem(news : News ,   fIrebaseDatabaseViewModel: FIrebaseDatabaseViewModel = hiltViewModel() , authFAuthViewModel: FAuthViewModel = hiltViewModel()){
            Spacer(modifier = Modifier.height(8.dp))
            val navigator = LocalNavigator.currentOrThrow
            Card(
                modifier = Modifier
                    //.align(Alignment.CenterHorizontally)
                    //.padding(5.dp)
                    .fillMaxWidth()
                    .height(500.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                onClick = {
                   // val detailArticle : DetailS = DetailS(news)
//                    newss = news
                    navigator.push(
                        NewsWebView(news)
                       // DetailArticle(news)
//                detailArticle
                    )
                }

            ) {
                //Spacer(modifier = Modifier.height(5.dp))
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = news.urlToImage)
                            .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                                //scale(Scale.FIT)
                                transformations(RoundedCornersTransformation())
                            }).build()
                    ), contentDescription = "News", modifier = Modifier
                        .fillMaxSize()

                        .weight(0.2f)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.SemiBold,
                    text = news.title
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row() {
                    Text(text = news.source.name)
                    Spacer(modifier = Modifier.width(70.dp))
                    Image(
                        modifier = Modifier
                            .size(15.dp)
                            .clickable {
                                fIrebaseDatabaseViewModel.storeData(news, authFAuthViewModel.userId)

                            }
                        ,
                        painter = painterResource(id = R.drawable.img), contentDescription = "Read Later"

                    )
                }

            }
        }

        @Composable
        fun NewsCall( category: String  , newsViewModel: NewsViewModel = hiltViewModel() ){
            val context = LocalContext.current

            if(newsViewModel.listResponse.isEmpty()){
                //Toast.makeText(context , newsViewModel.errorMessage, Toast.LENGTH_LONG).show()
                Log.d("Error" , newsViewModel.errorMessage)
            }
            newsViewModel.getNewss(category)
//        Toast.makeText(context , newsViewModel.errorMessage , Toast.LENGTH_LONG).show()

            newsListResponseC = newsViewModel.listResponse


            RecyclerView(newsList = newsListResponseC)

        }

        @Composable
        fun RecyclerView(newsList : List<News>  ,  newsViewModel: NewsViewModel = hiltViewModel()){
            LazyColumn{
                itemsIndexed(items = newsList){
                        index, item ->
                    NewsItem(news = item )
                }
            }
//        }

        }

        class DetailS (private val news : News): Screen{
            @Composable
            override fun Content() {
                DetailNews()
            }

            @SuppressLint("UnrememberedMutableState")
            @Composable
            fun DetailNews() {

                val scrobleState = rememberScrollState()
                val navigator = LocalNavigator.currentOrThrow

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .scrollable(scrobleState, Orientation.Vertical, true)

                ) {


                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = news.urlToImage)
                                .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                                    //scale(Scale.FIT)
                                    transformations(RoundedCornersTransformation())
                                }).build()
                        ), contentDescription = "News", modifier = Modifier
                            .fillMaxSize()

                            .weight(0.2f)
                    )

                    Row {
                        Text(
                            modifier = Modifier.weight(0.1f),
                            text = news.source.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light
                        )

                        Spacer(modifier = Modifier.width(15.dp))

//            Text(
//                text = news.author,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Light
//            )

                        //Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            modifier = Modifier.weight(0.1f),
                            text = news.publishedAt,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light
                        )
                    }


                    Text(
                        text = news.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))



                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = news.description,
                        fontWeight = FontWeight.Light
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Button(
                        onClick = {
                            val obj : NewsWeb = NewsWeb(news)
                            navigator.push((obj))
                        }) {

                        Text(
                            text = "Read More",
                            color = Color(0xFF0172d9)
                        )

                    }
                }
            }

        }





    }








//    @Composable
//    fun SearchBox(){
//        var text by remember { mutableStateOf("") }
//
//        Spacer(modifier = Modifier.height(5.dp))
//
//        TextField(
//            value = text,
//            onValueChange = { text = it },
//            label = { Text("Search") },
//            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(5.dp)
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//    }


//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    fun NewsItem(news : News , fIrebaseDatabaseViewModel: FIrebaseDatabaseViewModel = hiltViewModel() , authFAuthViewModel: FAuthViewModel = hiltViewModel()){
//        Spacer(modifier = Modifier.height(8.dp))
//        val navigator = LocalNavigator.currentOrThrow
//        Card(
//            modifier = Modifier
//                //.align(Alignment.CenterHorizontally)
//                //.padding(5.dp)
//                .fillMaxWidth()
//                .height(500.dp),
//            shape = RoundedCornerShape(10.dp),
//            elevation = CardDefaults.cardElevation(10.dp),
//            onClick = {
//                //val detailArticle : DetailArticle = DetailArticle(news)
//                navigator.push(
//                    DetailArticle(news)
////                detailArticle
//                )
//            }
//
//        ) {
//            //Spacer(modifier = Modifier.height(5.dp))
//            Image(
//                painter = rememberAsyncImagePainter(
//                    ImageRequest.Builder(LocalContext.current).data(data = news.urlToImage)
//                        .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
//                            //scale(Scale.FIT)
//                            transformations(RoundedCornersTransformation())
//                        }).build()
//                ), contentDescription = "News", modifier = Modifier
//                    .fillMaxSize()
//
//                    .weight(0.2f)
//            )
//            Spacer(modifier = Modifier.height(5.dp))
//            Text(
//                modifier = Modifier.padding(5.dp),
//                fontWeight = FontWeight.SemiBold,
//                text = news.title
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Row() {
//                Text(text = news.source.name)
//                Spacer(modifier = Modifier.width(70.dp))
//                Image(
//                    modifier = Modifier
//                        .size(15.dp)
//                        .clickable {
//                            fIrebaseDatabaseViewModel.storeData(news, authFAuthViewModel.userId)
//
//                        }
//                        ,
//                    painter = painterResource(id = R.drawable.img), contentDescription = "Read Later"
//
//                )
//            }
//
//        }
//    }

//    @Composable
//    fun RowItem(category : String ,  newsViewModel: NewsViewModel = hiltViewModel()){
//        Row(
//
//        ) {
//            Spacer(modifier = Modifier.width(5.dp))
//
//            OutlinedButton(
//                colors = ButtonDefaults.buttonColors(Color(0xFF0172d9)),
//                onClick = {
//
////                    if(newsViewModel.listResponse.isEmpty()){
////                        Log.d("Error" , newsViewModel.errorMessage)
////                    }
//
//                    newsViewModel.getNewss(category)
//                    newsListResponseC = newsViewModel.listResponse
//
//
//                }
//            ) {
//
//               //NewsCall(category = category)
//                Text(text = category)
//                Spacer(modifier = Modifier.height(15.dp))
//
//                //
//            }
//
//            Spacer(modifier = Modifier.height(15.dp))
//        }
//    }

//    @SuppressLint("UnrememberedMutableState")
//    @Composable
//    fun RowRecyclerView(){
//       // val categoryList = arrayOf("general" , "entertainment" , "business" , "health" , "science" , "sports" , "technology")
//        LazyRow(
//
//        ){
//            itemsIndexed(items = categoryList){
//                    index, item ->
//
//                RowItem(item )
//            }
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//    }

//    @Composable
//    fun RecyclerView(newsList : List<News> , newsViewModel: NewsViewModel = hiltViewModel()){
//            LazyColumn{
//                itemsIndexed(items = newsList){
//                        index, item ->
//                    NewsItem(news = item)
//                }
//            }
////        }
//
//    }


//    @Composable
//    fun RowScope.TabNavigationItems(tab:Tab) {
//
//        val tabNavigator = LocalTabNavigator.current
//        BottomNavigationItem(selected = tabNavigator.current == tab, onClick = { tabNavigator.current = tab },
//            icon = { Icon(tab.options.icon!!, contentDescription = tab.options.title )}
//        )
//
//    }

    





    object ProfileTab : Tab{

        override val options: TabOptions
            @Composable
            get() {

                val title = "Profile"
                val icon = rememberVectorPainter(Icons.Default.Person)

                return remember {
                    TabOptions(
                        1u,
                        title,
                        icon
                    )
                }

            }

        @Composable
        override fun Content() {
            Column(
                verticalArrangement  = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                UserData()
                Spacer(modifier = Modifier.height(10.dp))
                Settings()
            }
        }
        @Composable
        fun UserData() {
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .border(2.dp, Color(0xFF0172d9), RoundedCornerShape(5.dp))
                    .fillMaxWidth()
            ) {
                Row() {
                    Image(
                        modifier = Modifier.size(150.dp),
                        painter = painterResource(id = R.drawable.img_1),
                        contentDescription ="Profile_Image")
                    //Spacer(modifier = Modifier.width(10.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            fontSize = 24.sp,
                            text = "Mohit"
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "jkl@gmail.com"
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                        OutlinedButton(
                            onClick = {

                            }
                        ) {
                            Text(
                                text = "Change Password"
                            )
                        }
                    }
                }
            }
        }
        
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun Settings(){
            val country = arrayOf("India" , "USA" , "China" , "Australia" , "Canada" , " Germany")

            val language = arrayOf("English" , "Hindi" , "Urdu" , "French" , "Germany")

            var selectedLanguage by remember {
                mutableStateOf(language[0])
            }
            var selectedCountry by remember {
                mutableStateOf(country[0])
            }

            var expandedCountry by remember{
                mutableStateOf(false)
            }
            var expandedLanguage by remember {
                mutableStateOf(false)
            }
            Text(
                text = "Setting"
            )
            Spacer(modifier = Modifier.height(5.dp))
            Card(
                modifier = Modifier
                    .border(2.dp, Color(0xFF0172d9), RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .padding(7.dp)



            ) {
                Column() {

                    

                    ExposedDropdownMenuBox(
                        expanded = expandedCountry,
                        onExpandedChange = {
                            expandedCountry = !expandedCountry
                        },

                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {},
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCountry)
                            },
                            readOnly = true,
                            value = selectedCountry,
                        )
                        
                        

                        ExposedDropdownMenu(
                            expanded = expandedCountry ,
                            onDismissRequest = {
                                expandedCountry = false
                            }) {
                            country.forEach {
                                DropdownMenuItem(text = {
                                    Text(text = it)
                                }, onClick = {
                                    selectedCountry = it
                                    expandedCountry = false
                                })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    
                    ExposedDropdownMenuBox(
                        expanded = expandedLanguage,
                        onExpandedChange = {
                            expandedLanguage = !expandedLanguage
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {},
                            trailingIcon = {
                                           ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLanguage)
                            },
                            
                            readOnly = true,
                            value = selectedLanguage,
                            )
                        
                        ExposedDropdownMenu(
                            expanded = expandedLanguage ,
                            onDismissRequest = { 
                                expandedLanguage = false
                            }) {
                            language.forEach { 
                                DropdownMenuItem(text = {
                                                        Text(text = it)  
                                }, onClick = {
                                    selectedLanguage = it
                                    expandedLanguage = false
                                })
                            }
                        }
                        

                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = {

                        }
                    ) {
                        Text(text = "Save")
                    }
                }

            }


        }

        @Composable
        fun ReadLater(viewModel: FIrebaseDatabaseViewModel = hiltViewModel()){
              viewModel.readData()
        }

    }


    object SearchTab : Tab{

        override val options: TabOptions
            @Composable
            get() {

                val title = "Search"
                val icon = rememberVectorPainter(Icons.Default.Search)

                return remember {
                    TabOptions(
                        1u,
                        title,
                        icon
                    )
                }

            }

        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        override fun Content() {
            Scaffold(
                topBar = {
                    SearchBar()
                }
            ) {

            }
        }

        @OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)
        @Composable
        fun SearchBar() {
            // onSearch: (String) -> Unit
            var text by remember { mutableStateOf("") }
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current


            TextField(
                value = text,
                onValueChange = { text = it },
                label = {
                    Text(
                        color = Color(0xFF0172d9),
                        text = "Search")
                        },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color( 0xFF0172d9),
                    )
                              },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                   // onSearch(text)
                    // Hide the keyboard after submitting the search
                    keyboardController?.hide()
                    //or hide keyboard
                    focusManager.clearFocus()

                }),
                shape = RoundedCornerShape(10.dp),

            )
        }



    }


}





