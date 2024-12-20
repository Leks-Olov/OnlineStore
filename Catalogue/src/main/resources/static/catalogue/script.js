document.addEventListener('DOMContentLoaded', () => {
    const menuToggle = document.getElementById('menu-toggle');
    const sideMenu = document.getElementById('side-menu');
    const overlay = document.getElementById('menu-overlay');

    menuToggle.addEventListener('click', () => {
        console.log('Toggling side menu');
        sideMenu.classList.toggle('active');
        overlay.classList.toggle('active');
    });

    overlay.addEventListener('click', () => {
        console.log('Closing side menu via overlay');
        sideMenu.classList.remove('active');
        overlay.classList.remove('active');
    });

    document.getElementById('search-button').addEventListener('click', () => {
        const searchInput = document.getElementById('search-input').value;
        console.log('Searching for:', searchInput);
        // Implement search logic here
    });
});

