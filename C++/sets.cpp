#include <bits/stdc++.h>
using namespace std;
int main()
{
    set<int> s;
    s.insert(3);
    s.insert(2);
    s.insert(5);
    cout << s.count(3) << "\n"; // 1
    cout << s.count(4) << "\n"; // 0
    s.erase(3);
    s.insert(4);
    cout << s.count(3) << "\n"; // 0
    cout << s.count(4) << "\n"; // 1

    set<int> p = {2, 5, 6, 8};
    cout << p.size() << "\n"; //4
    for (auto x : p)
    {
        cout << x << " ";
    }
    cout << "\n";

    set<int> q;
    q.insert(5);
    q.insert(5);
    q.insert(5);
    cout << q.count(5) << "\n"; // 1
    for (auto x : q)
    {
        cout << x << " ";
    }
    cout << "\n";

    multiset<int> r;
    r.insert(5);
    r.insert(5);
    r.insert(5);
    cout << r.count(5) << "\n"; // 3
    for (auto x : r)
    {
        cout << x << " ";
    }
    cout << "\n";
    //r.erase(5);
    cout << r.count(5) << "\n";
    r.erase(r.find(5));
    cout << r.count(5) << "\n"; // 2
}