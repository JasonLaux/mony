import jwt_decode from "jwt-decode";
export function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
      let c = ca[i];

      // remove white space
      while (c.charAt(0) === ' ') {
        c = c.substring(1);
      }

      // get cvalue
      if (c.indexOf(name) === 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  }


// path=/ ?? expiredTime is minute unit
// name is the username and value is the token
// path parameter defines the path taht the cookie belongs to.
export function setCookie(cname, cvalue, exMinutes){
    const date = new Date();
    date.setDate(date.getTime() + (exMinutes*60*1000))
    const expires = "expires=" + date.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";" + "path=/";
    console.log(document.cookie)
}

// set the expired date in the past
export function deleteCookie(){
    setCookie('token', '', -1)
}

export function getUserId(){
  if(isLoggedIn()){
    const payload = jwt_decode(getCookie('token'))
    return payload.userId;
  }
  return null;
}

export function isLoggedIn(){
  return getCookie('token') !== '';
}

