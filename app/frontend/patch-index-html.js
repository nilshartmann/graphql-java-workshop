const fs = require("fs");

const content = fs.readFileSync("build/index.html", "utf8");

const newData = content.replace(/src="\//g, 'src="').replace(/href="\//g, 'href="');

fs.writeFileSync("build/index.html", newData);
