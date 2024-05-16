

@main
def SearchingAndSorting(): Unit = {
  val array1: Array[Int] = Array(7,2,6,1,5,4,3);
  val array2: Array[Int] = Array(7,2,6,1,5,4,3);
  val array3: Array[Int] = Array(7,2,6,1,5,4,3);
  val array4: Array[Int] = Array(7,2,6,1,5,4,3);
  val array5: Array[Int] = Array(7,2,6,1,5,4,3);
  val bubble = getAlgorithm("bubble");
  val insertion = getAlgorithm("insertion")
  val mergeSort = getAlgorithm("merge")
  val quicksort = getAlgorithm("quick")
  val radixSort = getAlgorithm("radix")
  bubble(array1);
  insertion(array2)
  mergeSort(array3)
  quickSort(array4)
  radixSort(array5)
  println("BUBBLE SORT")
  println(array1.mkString(", "))
  println("INSERTION SORT")
  println(array2.mkString(", "))
  println("MERGE SORT")
  println(array3.mkString(", "))
  println("QUICK SORT")
  println(array4.mkString(", "))
  println("RADIX SORT")
  println(array5.mkString(", "))

}

def getAlgorithm(algoType: String): (Array[Int]) => Unit = {
  algoType match
    case "bubble" => bubbleSort
    case "insertion" => insertionSort
    case "merge" => mergeSort
    case "quick" => quickSort
    case "radix" => radixsort
    case _ => (x) => println("Unknown sorting type")
}

def binarySearch(array: Array[Int], key: Int): Unit = {
  var low = 0;
  var high = array.length - 1;
  var breakLoop = false;
  while(low <= high && !breakLoop){
    var mid = (low + high)/2;
    if(array(mid) == key){
      println(s"$key found at index $mid")
      breakLoop = true;
    } else if (array(mid) > key){
      high -= 1;
    } else {
      low += 1;
    }
  }
}

def bubbleSort(array: Array[Int]): Unit = {
  val n: Int = array.length;
  for {
    i <- 0 until n
    j <- 0 until n-i-1
  } {
    if(array(j) > array(j+1)){
      swap(array, j, j+1);
    }
  }
}

def swap(array: Array[Int], i: Int, j: Int): Unit = {
  val temp = array(i);
  array(i) = array(j);
  array(j) = temp;
}

def insertionSort(arr: Array[Int]): Unit = {
  val n: Int = arr.length
  for (i <- 0 to n - 1) {
    var j = i
    while (j > 0 && arr(j - 1) > arr(j)) {
      val temp = arr(j - 1)
      arr(j - 1) = arr(j)
      arr(j) = temp
      j -= 1
    }
  }
}

def mergeSort(array: Array[Int]): Unit = {
  if(array.length <= 1) return
  else{
    val mid: Int = array.length / 2;
    val arr1: Array[Int] = array.slice(0, mid);
    val arr2: Array[Int] = array.slice(mid, array.length);
    mergeSort(arr1)
    mergeSort(arr2)
    val mergedArray = merge(arr1, arr2)
    for(i <- array.indices){
      array(i) = mergedArray(i);
    }
  }
}

def merge(arr1: Array[Int], arr2: Array[Int]): Array[Int] = {
  val n: Int = arr1.length;
  val m: Int = arr2.length;
  val mergedArray = Array.ofDim[Int](n + m)

  var i: Int = 0
  var j: Int = 0
  var index: Int = 0

  while(i < n && j < m){
    if(arr1(i) <= arr2(j)) {
      mergedArray(index) = arr1(i)
      i += 1;
    } else{
      mergedArray(index) = arr2(j)
      j += 1;
    }
    index += 1;
  }

  if(i < n){
    while( i < n){
      mergedArray(index) = arr1(i)
      index += 1
      i += 1
    }
  } else if (j < m) {
    while (j < m) {
      mergedArray(index) = arr2(j)
      index += 1
      j += 1
    }
  }
  return mergedArray
}

def quickSort(array: Array[Int]): Unit = {
  val n:Int = array.length
  helper(array, 0, n-1);
}

def helper(array: Array[Int], low: Int, high: Int): Unit = {
  if (low < high) {
    val pi = partition(array, low, high)
    helper(array, low, pi - 1)
    helper(array, pi + 1, high)
  } else {
    return
  }
}

def partition(arr: Array[Int], low: Int, high: Int): Int = {

  val pivot = arr(high)

  var i = low - 1
  for (j <- low to high - 1) {

    if (arr(j) < pivot) {

      i += 1
      swap(arr, i, j)
    }
  }
  swap(arr, i + 1, high)
  i + 1
}

def getMax(arr: Array[Int]): Int = {
  val n: Int = arr.length;
  var max = arr(0)
  for (i <- 1 until n) {
    if (arr(i) > max) max = arr(i)
  }
  max
}

def countSort(arr: Array[Int], n: Int, exp: Int): Unit = {
  val output = new Array[Int](n)
  val count = new Array[Int](10)

  var i = 0
  while (i < n) {
    count((arr(i) / exp) % 10) += 1
    i += 1
  }

  i = 1
  while (i < 10) {
    count(i) += count(i - 1)
    i += 1
  }

  i = n - 1
  while (i >= 0) {
    output(count((arr(i) / exp) % 10) - 1) = arr(i)
    count((arr(i) / exp) % 10) -= 1
    i -= 1
  }

  i = 0
  while (i < n) {
    arr(i) = output(i)
    i += 1
  }
}

def radixsort(arr: Array[Int]): Unit = {
  val n: Int = arr.length
  val m = getMax(arr)
  var exp = 1
  while (m / exp > 0) {
    countSort(arr, n, exp)
    exp *= 10
  }
}
