Java project that reads lat and lng from a .csv and returns corrected elevation using Google Elevation API. 

7.31.14 Note: Compile and run with javac/java -classpath .;lib\json-20140107.jar ElevationCorrection

Google Elevation API Limits:

Users of the free API:
2,500 requests per 24 hour period.
512 locations per request.
25,000 total locations per 24 hour period.
10 requests per second.


Maps for Business customers:
100,000 requests per 24 hour period.
1,000,000 total locations per 24 hour period.
10 requests per second.