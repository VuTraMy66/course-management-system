document.addEventListener("DOMContentLoaded", function () {
    // Create a navigation bar dynamically
    const navBar = `
        <nav>
            <ul>
                <li><a href="/">Home</a></li>
                <li><a href="/courses">Courses</a></li>
                <li><a href="/about">About</a></li>
            </ul>
        </nav>
    `;

    // Insert the navigation bar at the top of the body
    document.body.insertAdjacentHTML('afterbegin', navBar);
});
