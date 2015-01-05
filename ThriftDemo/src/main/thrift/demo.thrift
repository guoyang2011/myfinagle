#@namespace scala cn.changhong.core.thrift

struct User{
 1:i64 id;
 2:string username;
 3:string iphone;
 4:string email;
 5:string passwd;
}
service AccountService{
 bool create(1:User user);
 User get(1:i64 id);
 list<User> list();
 map<string,User> map();
 list<bool> creates(list<User> users);
}
