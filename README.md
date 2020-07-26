＃SM
The function of the FileUtils class is to create a TXT file to store the number of males and females entered. 
Therefore, you first need to run FileUtils clss. (Please refer to the wmmatch.txt file in the root directory, which contains the test data, and the number of people tested is 50.).
When the .TXT file is generated, run the GS algorithm or the EGS algorithm. 
The function of CheckUtil is to detect whether there is a blocking pair in the matching result, so this class is called every time the algorithm is run.
After testing, both algorithms can obtain stable matching results.
26/07/2020，The function of detecting whether the input value is an integer is added to the FileUtils Class to ensure that the input value is an integer. If the input is not an integer, an exception will be thrown.
