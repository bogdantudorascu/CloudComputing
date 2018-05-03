<jsp:include page="components/header.jsp" >
  <jsp:param name="title" value="Not Found" />
 </jsp:include>
 
 <jsp:include page="components/navbar.jsp" />

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>
                    Oops!</h1>
                <h2>
                    404 Not Found</h2>
                <div class="error-details">
                    Sorry, an error has occured, Requested page not found!
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp" />