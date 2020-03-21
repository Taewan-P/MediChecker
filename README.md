# MediChecker

DSC Solution Challenge 2020 - ThreeCSEDevs from DSC Hanyang Univ. ERICA

<img src="https://pbs.twimg.com/tweet_video_thumb/ETPxX6cUwAAUuZd.jpg" width="700">

MediChecker helps you to take medicine safely. It can check interactions between multiple medicines. Interaction data is based on [RxNav](https://rxnav.nlm.nih.gov/index.html) of NLM(National Library of Medicine).


## Table Of Contents
1. [For Developments](https://github.com/Taewan-P/MediChecker#for-developments)
- [Requirements](https://github.com/Taewan-P/MediChecker#requirements)
- [Build an App](https://github.com/Taewan-P/MediChecker#build)
2. [For Users](https://github.com/Taewan-P/MediChecker#for-users)
- [Download Link](https://github.com/Taewan-P/MediChecker#download)
- [How to Use](https://github.com/Taewan-P/MediChecker#how-to-use)
- [Search Interaction](https://github.com/Taewan-P/MediChecker#search-interaction)
- [User Profile](https://github.com/Taewan-P/MediChecker#user-profile)
3. [Disclaimer](https://github.com/Taewan-P/MediChecker#declamier)


## For Developments
### Requirements
#### Environment
* Android Studio ^3.6
* JDK >= 1.8.0_241
* Android SDK > 23

#### Dependencies
All of the dependencies are in `build.gradle`.
* Android SDK
* core-ktx ^1.0.2
* [Smooth bottom bar](https://github.com/ibrahimsn98/SmoothBottomBar)
* volley ^1.1.1
* Coroutine ^1.3.4

### Build
* Sync dependencies using `Gradle sync`
* Build apk using Android Studio


## For Users
### Download
You can find the release build of our application here.

[Download](https://github.com/Taewan-P/MediChecker/releases)

And the archive of earlier build can found here.

[Releases](https://github.com/Taewan-P/MediChecker/releases)

### How to Use
#### Search Interaction
In the second tab, you can search for interactions between multiple medicines. Just tap the `Input Medicine` button to search your medicine name or active ingredients like `Simvastatin`. Our application will automatically search your inputs and show suggestions. 

If you want to modify an item, tap the button again.

You can add more medicines using `ADD` button and delete items using `DELETE` button.

After completing all your inputs, tap `SEARCH INTERACTION` button to find results online.
![]()![]()![]()![]()



#### User Profile
You can set your info: Name, Age, Height, and Weight. 



## Disclaimer

All of the information that our application provide is to provide users with information to better understand their health and their medications, and is not intended for medical advice, diagnosis or treatment. Be sure to consult with a qualified physician for advice about medications.

Data sources include NLM's RxNorm API and others.

NLM
> It is not the intention of NLM to provide specific medical advice, but rather to provide users with information to better understand their health and their medications. NLM urges you to consult with a qualified physician for advice about medications.

DrugBank

> [https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3422823/](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3422823/)