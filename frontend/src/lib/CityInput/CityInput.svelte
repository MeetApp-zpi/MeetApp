<script>
    import Svelecte from 'svelecte';
  
    let options = [];
  
    let selection = null;
    let value = 'cz';

    // execute('city', 'GET')
    //     .then((r) => r.json())
    //     .then((r) => (options = r));
  
    setTimeout(() => {
      value = 'de';
    }, 4000);
</script>
  
<Svelecte {options} 
class="bg-ivory text-gray border-2 border-tea
text-2xl rounded-lg w-full block
focus:text-cocoa"
    inputId="city"
    bind:readSelection={selection}
    bind:value={value}
    placeholder="Miasto">
</Svelecte>

<style>
  :global(.sv-control) {
      padding: 10px;
  }
</style>
  
  <!-- <div>Current <code>readSelection</code> value: <b>{JSON.stringify(selection) }</b></div>
  <div>Current <code>value</code> value: <b>{value}</b></div> -->