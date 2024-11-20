# Poplar: Micro Blog

- Check the website out - https://ndkshr.github.io/ndkshrPoplar
- Reads and Writes to repository - https://github.com/ndkshr/ndkshrPoplar

## What is Poplar?
It is a lightweight Micro Blogger/Journal app. Poplar uses the JGit library to push directly to a repository of your choice. You can customize the look and feel of the blog list, as it is plain old HTML and some CSS. I personally started mine by exporting a Google Docs document as HTML XD. It lacks many features, so please don't compare it to X (f.k.a. Twitter) or Medium. But I wouldn't complain if I get to Journal for free while maintaining the platform.

<img width="250" alt="image" src="https://github.com/user-attachments/assets/f02afb26-fb9a-4dfa-8bd7-fd1578ea8108">
<img width="250" alt="image" src="https://github.com/user-attachments/assets/1c4a709a-6b2b-4039-8f95-05494f877a2f">



## Getting Started
If you are not a big fan of getting your hands dirty, you can bootstrap the web page and the Android app (sorry, no iOS; you guys stick to the paid stuff).
Replace the `Constants.kt.txt` file with your token, repository, etc.

## Future Features
- Edit & Delete blogs.
- Support Image Posts - `ImageBlog(Title, Image, Tags)`.
- Replace HTML with JSON (I want to work with ProtoBuf/FlatBuf, I might try some nonsense).
- Since everything is one huge document, I was wondering if a small RAG can be run on the client side??

> PS: I will add more details about setting up soon, as I want to make the app and website a bit more stable.
