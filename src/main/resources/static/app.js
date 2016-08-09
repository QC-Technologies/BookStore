'use strict;'
const React = require('react');
const client = require('./client');
const cookie = require('js.cookie');
const JSON = require('JSON');
const $ = require('jquery');
const LOCAL_HOST_GET_ID = 'http://localhost:8080/get_id';
const LOCAL_HOST_GET_CATEGORIES = 'http://localhost:8080/api/bookCategories'

var BookStore = React.createClass({
    componentWillMount: function(){
        React.render(<SearchForm/>, document.getElementById('searchForm'));
    },
    getInitialState: function(){
        return{data:[]}
    },
    render: function(){
        return(
            <div className="container">
                <h3 className='haider'>Book Store</h3>
                <BookList data={this.state.data} />
            </div>
        );
    },
    componentDidMount: function(){
        client({method:'GET', path:'api/books'}).done(response => {
            this.setState({data: response.entity._embedded.books});
        });
    }
});

var BookList = React.createClass({
    render: function(){
        var BookNode = this.props.data.map(function(book){
            var lastIndex = book._links.self.href.lastIndexOf('/');
            var id = book._links.self.href.substring(lastIndex+1);
            return(
                    <Book title={book.title} quantity={book.quantity} id={id} author={book.author} description={book.decription} price={book.price} image={book.image} />
                                          );
                                           });
        return(
            <ul>
                {BookNode}
            </ul>
            );
    }
});

var Book = React.createClass({
    addToCart: function(){
        client({method:'GET', path:'api/books/'+this.props.id}).done(response => {
            var cart = cookie.get('cart');
            if (!cart){
                cart = [];
            }
            //Find if object is already in cart increase it's quantity
            var dirty_flag = false;
            var e = response.entity;
            if(e.quantity>0){
                            for(var i=0; i<cart.length; i++){
                var book = cart[i];
                if(book.title === e.title){
                    book.quantity += 1;
                    dirty_flag = true;
                }
            }
            if(!dirty_flag){
                e.quantity = 1;
                cart.push(e);
            }
            cookie.set("cart", cart);
            e = response.entity;
            $.ajax({
                url: 'http://localhost:8080/add_to_cart',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(e),
                success: function(data){
                },
                error: function(err){
                    console.log(err.message);
                }
            });
            React.render(<Cart data={cookie.get('cart')}/>, document.getElementById('sticker')
                                                                    );
            React.render(<BookStore/>,
            document.getElementById('react'));
            }

        });
    },
    render: function(){
        if(this.props.quantity>0){
            return(
            <div className="templatemo_product_box">
                <h1>{this.props.title}  <span>(by {this.props.author})</span></h1>
                <img src={this.props.image} alt="image" />
                <div className="product_info">
                    <p>{this.props.description}</p>
                    <h3>${this.props.price}</h3>
                    <div className="buy_now_button"><button id={this.props.id} onClick={this.addToCart}>Buy Now</button></div>
                </div>
                <div className="cleaner">&nbsp;</div>
            </div>
            
            );
        }else{
            return(
            <div className="templatemo_product_box">
                <h1>{this.props.title}  <span>(by {this.props.author})</span></h1>
                <img src={this.props.image} alt="image" />
                <div className="product_info">
                    <p>{this.props.description}</p>
                    <h3>${this.props.price}</h3>
                    <div className="buy_now_button"><button id={this.props.id}>Buy Now</button></div>
                </div>
                <div className="cleaner">&nbsp;</div>
            </div>
            
            );
        }
        
    }
});

var CartEntity = React.createClass({
    updateDatabase: function(book){
        $.ajax({
                url: 'http://localhost:8080/remove_from_cart',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(book),
                success: function(data){
                }
            });
    },
    removeFromCart: function(){
        var cart = cookie.get('cart');
        for(var i=0; i< cart.length; i++){
            var temp = cart[i];
            if(temp.title === this.props.title){
                this.updateDatabase(temp);
                        cart.splice(i,1);
            }
        }
        cookie.set('cart',cart);
        React.render(<Cart data={cookie.get('cart')}/>, document.getElementById('sticker')
                                                                    );
    },
    render: function(){
        return(
        <li className="cart_item">{this.props.title}  {this.props.quantity}  ${this.props.price} 
            <button onClick={this.removeFromCart}>Remove</button></li>
        );
    }
});

var CartList = React.createClass({
    render: function(){
        var Node = this.props.data.map(function(book){
        var id;
        //getting Id against title
        var id = $.ajax({
           url: LOCAL_HOST_GET_ID,
           type: 'GET',
           data: {'title':book.title},
           success: function(data){
              id = data;
           },
           error: function(err){
               alert(JSON.stringify(err.message));
           }
        });
            if(id){
                return(
                    <CartEntity title={book.title} quantity = {book.quantity} price={book.price} id={id} />
                                          );
            }
                return(
                    <CartEntity title={book.title} quantity = {book.quantity} price={book.price} />
                                          );
            
                                           });
        return(
            <ul>
                {Node}
            </ul>
            );
    }
        
    
});


var SearchForm = React.createClass({
    search: function(){
        var title = $("#search").val();
        if(title != ''){
            $.ajax({
            url: 'http://localhost:8080/search',
            data:{'title': title},
            success: function(data){
                React.render(<SearchResult data={JSON.parse(data)}/>,
                document.getElementById('react'));
            }
            });
        }
        
    },
    render: function(){
        return(
                    <div>
                        <button className="search_form" onClick={this.search}>Search</button>
                        <input className="search_form" id="search" type="text" placeholder="Search Book"></input>
                        
                    </div>
        );
    }
});


var SearchResult = React.createClass({
    render: function(){
        return(
            <div className="container">
                <h3 className='haider'>Search Result</h3>
                <SearchList data={this.props.data} />
            </div>
        );
    }
});

var SearchList = React.createClass({
    render: function(){
        var BookNode = this.props.data.map(function(book){
            return(
                    <Book title={book.title} quantity={book.quantity} id={book.id} author={book.author} description={book.decription} price={book.price} image={book.image} />
                                          );
                                           });
        return(
            <ul>
                {BookNode}
            </ul>
            );
    }
});
        
var Cart = React.createClass({
    render: function(){
       return( 
           <div>
           <div><h1>Cart</h1></div>
           <CartList data={cookie.get('cart')} />
            <div><button className="checkout">checkout</button></div>
            </div>
            );
    }
});
if(cookie.get('cart')){
React.render(<Cart data={cookie.get('cart')}/>, document.getElementById('sticker')
                                                                    );
}




var CategoryBox = React.createClass({
    getInitialState: function(){
        return{data:[]}
    },
    render: function(){
        return(
            <ul>
                <CategoryList data={this.state.data}/>
            </ul>
        );
    },
    componentDidMount: function(){
        client({method:'GET', path:'api/bookCategories'}).done(response => {
            this.setState({data: response.entity._embedded.bookCategories});
        });
    }
});



var CategoryList = React.createClass({
    render: function(){
        var CatNode = this.props.data.map(function(category){
            return(
                    <Category name={category.name} />
                                          );
                                           });
        return(
            <ul>
                {CatNode}
            </ul>
            );
    }
});

var Category = React.createClass({
    render: function(){
        return(
            <li><a href="#">{this.props.name}</a></li>
        );
        
    }
});

React.render(<CategoryBox/>, document.getElementById('categories_show'));

React.render(<BookStore/>,
            document.getElementById('react'));
//Sticky cart
    $(document).ready(function() {
    var s = $("#sticker");
    var pos = s.position();
    $(window).scroll(function() {
        var windowPos = $(window).scrollTop();
        s.animate({'margin-top':windowPos+10+'px'},20);
        if (windowPos >= pos.top) {
            s.addClass("stick");
        } else {
            s.removeClass("stick");
        }
    });
});