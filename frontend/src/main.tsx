import React from 'react';
import ReactDOM from 'react-dom/client';
{/*import './assets/css/style.css';
import PrimaryButton from './components/buttons/primaryButton';
import TinyButton from "./components/buttons/tinyButton.tsx";
import PageTitle from "./components/pageTitle/pageTitle.tsx";
*/}
import IconButton from "./components/buttons/iconButton.tsx";
import './assets/css/global.css';



import Layout from "./components/layout/layout.tsx";

const App: React.FC = () => {
    const handleButtonClick = () => {
        alert('Button clicked!');
    };

    {/*const handleNotificationClose = () => {
        alert('Notification closed!');
    };*/}

    return (
        <Layout>
            <IconButton onClick={handleButtonClick} icon="logout">Home</IconButton>

            {/*<div>
               <PageTitle title="Buttons list" />
                <div>
                    <PrimaryButton onClick={handleButtonClick}>Click Me</PrimaryButton>
                    <TinyButton onClick={handleButtonClick} icon="home" />
                    <IconButton onClick={handleButtonClick} icon="logout">Home</IconButton>
                    <Notification
                        type="error"
                        title="Texto confirmacion"
                        message="Mensaje de exito."
                        onClose={handleNotificationClose}
                    />
                </div>
            </div>*/}
        </Layout>

);
};

const rootElement = document.getElementById('app');

if (rootElement) {
    const root = ReactDOM.createRoot(rootElement);
    root.render(<App />);
} else {
    console.error("No se encontró el elemento con id 'app'.");
}
