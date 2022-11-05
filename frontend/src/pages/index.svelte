<script lang="ts">
    import Footer from '../lib/Footer/Footer.svelte';
    import Header from '../lib/Header/Header.svelte';
    import Button from '../lib/Button/Button.svelte';
</script>

<Header />
<h2>index</h2>
<a href="/users" target="_self">click link</a>
<Button clickHandler={() => console.log('works')}>Zapisuję się!</Button>
<Footer pageType="main" />