# Comments
 
 Project for fetching data from jsonplaceholder.typicode.com and save it into local directory
 
## Endpoints
Application have just one endpoint:
 
 `/fetchComments/{size}` work as follows: 
  - Fetches _size_  posts with comments from jsonplaceholder.typicode.com.
  - Then extracts email's domain from each comment. 
  - Creates folder named same as domain.
  - Saves all comments from given domain as separate files in domain folder.
 
 
 
## Configuration
Configuration contains just two properties:
 
 - **posts.service.path** - it should not be changed because it could fetch unexpected data 
 - **comments.dir.path** - directory where data is saved 

## Run
Use maven for build project 