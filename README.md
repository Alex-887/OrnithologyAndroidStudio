# Ornithology

This project was realised during my 4th semester at the HES-SO Business IT, with my partner Léonard Favre.

The goal was to create a firebase & room database with basic CRUD operation.







# Firebase version : Ornithology_Favre_Berthouzoz

login :     a@a.com 
password :  123456

Logout : press the red button on the main menu. The main menu is the screen with 3 buttons and a blue bird logo.


On the welcome screen, you have different features : 

Find a bird, where you can see all families and when clicking on it, you can access to a list with all birds and when clicking on a specific birds, you can get its infos.

The about is a a presentation about the project

The settings where you can chose your theme.

On a list (wheter bird or family) : 
- Update by swiping on the left
- Delete by swiping on the right
- Add by clicking on the add button

Toolbar :
- Delete all on the top corner right
- Back to settings on the top corner right



Hope you will enjoy our application
Léonard F & Alex B


JSON tree :

```json
{
	"birds" : {
		"{familyId1}" : {
			"familyName" : "Alpine predators"
			"birds" : {
				"{birdId1}" : {
					  "name": "Common coucou",
					  "description": "This is a coucou",
					  "bio": "The biology of a coucou"
				},
				"{birdId2}" : {
					  "name": "Common coucou",
					  "description": "This is a coucou",
					  "bio": "The biology of a coucou"
				}
			}
		}, 
		"{familyId2}" : {
			"familyName" : "Martinets"
			"birds" : {
				"{birdId3}" : {
					  "name": "Common coucou",
					  "description": "This is a coucou",
					  "bio": "The biology of a coucou"
				}
			}
		},
		...
	}
	
	"families" : {
		"{familyId1}" : {
			"familyName" : "Alpine predators"
		}, 
		"{familyId2}" : {
			"familyName" : "Martinets"
		},
		...
	}
}

