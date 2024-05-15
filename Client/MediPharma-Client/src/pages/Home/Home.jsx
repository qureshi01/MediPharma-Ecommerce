import React, { useState }from 'react'
import './Home.css'
import Header from '../../components/Header/Header'
import ExploreMenu from '../../components/CategoryMenu/CategoryMenu'
import FoodDisplay from '../../components/ProdDisplay/ProdDisplay'
import AppDownload from '../../components/AppDownload/AppDownload'

const Home = ({ showLogin }) => {

    const [category,setCategory] = useState("All");

  return (
    <div>
        <Header/> 
        <ExploreMenu category={category} setCategory={setCategory}/>
        <FoodDisplay showLogin={showLogin} category={category}/>
        <AppDownload/>
    </div>
  )
}

export default Home