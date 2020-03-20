# MediChecker

DSC Sollution Challenge 2020 - ThreeCSEDevs from DSC Hanyang Uviv. ERICA

<img src="https://pbs.twimg.com/tweet_video_thumb/ETPxX6cUwAAUuZd.jpg" width="700">

MediChecker helps take medicine regularly and safely. It offers check interaction between multiple medicine, before take them and set alarm notification to take the medicine regularly.
Interaction data are based on [RxNav](https://rxnav.nlm.nih.gov/index.html) of NLM(National Library of Medicine).


## Table Of Contents
1. [For Developments](https://github.com/Taewan-P/MediChecker#for-developments)
- [Requirements](https://github.com/Taewan-P/MediChecker#requirements)
- [Build an App](https://github.com/Taewan-P/MediChecker#build)
2. [For Users](https://github.com/Taewan-P/MediChecker#for-users)
- [Download Link](https://github.com/Taewan-P/MediChecker#download)
- [How to Use](https://github.com/Taewan-P/MediChecker#how-to-use)
- [Add Alarm](https://github.com/Taewan-P/MediChecker#add-notification-alarams)
- [Search Interaction](https://github.com/Taewan-P/MediChecker#search-interaction)
- [User Profile](https://github.com/Taewan-P/MediChecker#user-profile)
3. [Declamier](https://github.com/Taewan-P/MediChecker#declamier)


## For Developments
### Requirements
#### Environment
* Android Studio ^3.6
* JDK >= 1.8.0_241
* Android SDK > 23

#### Dependencies
All they were already written `build.gradle`. You can get all of them just gradle sync.
* Android SDK
* core-ktx ^1.0.2
* [Smooth bottom bar](https://github.com/ibrahimsn98/SmoothBottomBar)
* volley ^1.1.1
* Coroutine ^1.3.4

### Build
* Sync dependencies using `gradle sync`
* build your own apk using Android Studio


## For Users
### Download
You can find release build of our applicatin Here.

[Download](https://github.com/Taewan-P/MediChecker/release)

And the achive of earlier build can found here.

[Releases](https://github.com/Taewan-P/MediChecker/release)

### How to Use
#### Add notification alarams
In the first tab, you can add notification alarams not to forget to take medicines.
Just tap `+` button and set time when you wish to get notification message.
![]() ![]()

#### Search Interaction
In the second tab, you can search interaction between several medicines.
Just tap `Input Medicine` button to search your medicine name like `Simvastatin`. Our applicaion will automatically search your inputs and show suggestions.
If you want to modify items, tap the button again.

You can add more medicines using `ADD` button and delete items using `DELETE` button.

After complete all your inputs, tap `SEARCH INTERACTION` button to find results online.
![]()![]()![]()![]()



#### User Profile
You can set your personal info like age, height, weight. 



## Declamier

All of the Informations of our application provides are just for educatinal purpose only and is not intended for medical advice, diagnosis or treatment.
Data source include NLM's RxNorm API and others.

NLM
> It is not the intention of NLM to provide specific medical advice, but rather to provide users with information to better understand their health and their medications. NLM urges you to consult with a qualified physician for advice about medications.

DrugBank
> [https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3422823/](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3422823/)