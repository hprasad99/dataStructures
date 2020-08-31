#include <bits/stdc++.h>
using namespace std;
int main()
{
    vector<int> v;
    v.push_back(3);
    v.push_back(2);
    v.push_back(5);

    cout << v[0] << "\n";
    cout << v[1] << "\n";
    cout << v[2] << "\n";

    for (int i = 0; i < v.size(); i++)
    {
        cout << v[i] << "\n";
    }

    for (auto x : v)
    {
        cout << x << "\n";
    }

    //pop_back removes the last element
    cout<<v.back()<<"\n";
    v.pop_back();
    cout<<v.back()<<"\n";

    vector<int> v={2,4,2,5,1};

    //size 10, initial value 5
    vector<int> v(10,5);

}