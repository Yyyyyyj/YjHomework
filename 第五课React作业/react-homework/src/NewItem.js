import React, { Component } from 'react';
import './TodoList.css';

class NewItem extends Component{

   constructor(props){
       super(props);
       this.state = {
           inputContent:''
       }
   }

   onInputChange = (event) => {
       this.setState(
           {inputContent: event.target.value}
       )
   }
   onAddBtnClick = () => {
       if(this.state.inputContent.trim() === ''){
            window.alert("请填入list内容");
       }else{
            this.props.addNewItem(this.state.inputContent)
            this.setState({
                inputContent: ''
            })
       }
}

   render(){
    return (
        <tr>
            <th>
                <input type="text" value={this.state.inputContent} onChange={this.onInputChange}/>
            </th>
            <th>
                <button className="btn" onClick ={this.onAddBtnClick}>添加list</button>
            </th>
        </tr>
    );
}
}

export default NewItem;