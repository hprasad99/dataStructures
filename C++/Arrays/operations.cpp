/*
    @author: Himanshu Prasad
*/

#include<iostream>
using namespace std;

const int MAX = 5;
class array{
    private:
    int arr[MAX];
    int i;


    public:
    void insert(int pos,int num);
    void del(int pos);
    void reverse();
    void display();
    void search(int num);
};



void array :: insert(int pos,int num){
    for(i=MAX-1;i>=pos;i--){
        arr[i] = arr[i-1];
    }
    arr[i] = num;
}

void array :: del(int pos){
    for(i=pos;i<MAX;i++){
        arr[i-1]=arr[i];
    }
    arr[i-1]=0;
} 

void array :: reverse(){
    for(i=0;i<MAX/2;i++){
        int temp = arr[i];
        arr[i] = arr[MAX-1-i];
        arr[MAX-1-i]=temp;
    }
}

void array :: search(int num){
    //Traverse the array
    for(i=0;i<MAX;i++){
        if(arr[i]==num){
            cout<<"\n\n The element "<<num<<" is present at "<<(i+1)<<"th position\n";
            return;
        }
    }
    if(i==MAX){
        cout<<"\n\n The element"<<num<<" is not present in the array\n";
    }
}
//displays the content of array
void array :: display(){
    cout<<endl;
    for(i=0;i<MAX;i++){
        cout<<" "<<arr[i];
    }
}

int main(){
    array a;
    a.insert(1,11);
    a.insert(2,12);
    a.insert(3,13);
    a.insert(4,14);
    a.insert(5,15);

    cout<<"\nElements of Array: ";
    a.display();

    a.del(5);
    a.del(2);
    
    cout<<"\n\nAfter deletion: ";
    a.display();

    a.insert(2,222);
    a.insert(5,555);

    cout<<"\n\nAfter insertion: ";
    a.display();

    a.reverse();
    cout<<"\n\nAfter reversing: ";
    a.display();

    a.search(222);
    a.search(666);


}


