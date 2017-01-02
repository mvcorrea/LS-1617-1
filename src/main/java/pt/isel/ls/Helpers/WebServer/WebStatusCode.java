package pt.isel.ls.Helpers.WebServer;


public enum WebStatusCode {
    Ok(200),
    Created(201),
    SeeOther(303),
    BadRequest(400),
    NotFound(404),
    MethodNotAllowed(405),
    InternalServerError(500),
    ;

    private final int _code;
    WebStatusCode(int code){
        _code = code;
    }
    public int valueOf() {
        return _code;
    }
}
