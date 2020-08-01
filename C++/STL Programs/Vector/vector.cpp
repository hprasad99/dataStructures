#include <iostream>
#include <vector>
using namespace std;
int main()
{

    //Part-1
    cout << "\n**********************\n";
    vector<string> v1;
    v1.push_back("Python ");
    v1.push_back("Java");
    for (vector<string>::iterator itr = v1.begin(); itr != v1.end(); ++itr)
    {
        cout << *itr << "\n";
    }
    //cout<<"\n**********************\n";

    //Part-2 ==> v.at()
    cout << "\n**********************\n";
    static const int arr[] = {1, 2, 3, 4};
    vector<int> v2(arr, arr + sizeof(arr) / sizeof(arr[0]));
    for (int i = 0; i < v2.size(); i++)
    {
        cout << v2.at(i) << "\n";
    }
    //cout<<"\n**********************\n";

    //Part-3 ==> v.back()
    cout << "\n**********************\n";
    string arr1[] = {"mango", "apple", "banana"};
    vector<string> v3(arr1, arr1 + sizeof(arr1) / sizeof(arr1[0]));
    cout << v3.back() << "\n";
    //cout<<"\n**********************\n";

    //Part-4 ==> v.front()
    cout << "\n**********************";
    string arr2[] = {"java", "python", "c++"};
    vector<string> v4(arr2, arr2 + sizeof(arr2) / sizeof(arr2[0]));
    cout << v4.front() << "\n";
    //cout<<"\n**********************";

    //Part-5 ==> v1.swap(v2)
    cout << "\n*********************";
    int arr3[] = {1, 2, 3, 4, 5};
    int arr4[] = {6, 7, 8, 9, 10};
    vector<int> v5(arr3, arr3 + sizeof(arr3) / sizeof(arr3[0]));
    vector<int> v6(arr4, arr4 + sizeof(arr4) / sizeof(arr4[0]));
    cout << "\n Before Swapping elements of v5: ";
    for (vector<int>::iterator itr1 = v5.begin(); itr1 != v5.end(); ++itr1)
    {
        cout << *itr1 << " ";
    }
    cout << "\n Before Swapping elements of v6: ";
    for (vector<int>::iterator itr2 = v6.begin(); itr2 != v6.end(); ++itr2)
    {
        cout << *itr2 << " ";
    }
    v5.swap(v6);
    cout << "\n";
    cout << "\n After Swapping elements of v5: ";
    for (vector<int>::iterator itr1 = v5.begin(); itr1 != v5.end(); ++itr1)
    {
        cout << *itr1 << " ";
    }
    cout << "\n After Swapping elements of v6: ";
    for (vector<int>::iterator itr2 = v6.begin(); itr2 != v6.end(); ++itr2)
    {
        cout << *itr2 << " ";
    }

    //Part-6 v.pop_back()
    cout << "\n**********************";
    string arr5[] = {"welcome", "to", "javaTpoint", "tutorial"};
    vector<string> v7(arr5, arr5 + sizeof(arr5) / sizeof(arr5[0]));
    cout << "\n Initial string is: ";
    for (vector<string>::iterator itr = v7.begin(); itr != v7.end(); ++itr)
    {
        cout << *itr << " ";
    }
    v7.pop_back();
    cout << "\n After deleting last string, string is :";
    for (vector<string>::iterator itr = v7.begin(); itr != v7.end(); ++itr)
    {
        cout << *itr << " ";
    }

    //Part-7 v.empty()
    cout << "\n**********************";
    if (v7.empty())
    {
        cout << "\n Vector v7 is empty";
    }
    else
    {
        cout << "\n Vector v7 is not empty";
    }

    //Part-8 v.insert()
    //insert(iterator,val);
    //insert(iterator,n,val);
    //insert(iterator,InputIterator first,InputIterator last);
    // iterator defines the position, where the new elements are to be inserted
    // val specifies the value to be inserted
    // number of times the value is to be occurred
    cout << "\n**********************\n";
    string arr6[] = {"Java"};
    vector<string> v8(arr6, arr6 + sizeof(arr6) / sizeof(arr6[0]));
    string str = "programs";
    v8.insert(v8.begin() + 1,2, str);
    for (vector<string>::iterator itr = v8.begin(); itr != v8.end(); ++itr)
    {
        cout << *itr << " ";
    }

    return 0;
}